package alysondev.lanchonete.services;


import alysondev.lanchonete.entity.Pedido;
import alysondev.lanchonete.execption.PedidoNaoEncontradoException;
import alysondev.lanchonete.payment.StatusPagamento;
import alysondev.lanchonete.payment.Transacao;
import alysondev.lanchonete.payment.dtos.TransacaoResponseDTO;
import alysondev.lanchonete.payment.repository.TransacaoRepository;
import alysondev.lanchonete.repository.PedidoRepository;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final TransacaoRepository transacaoRepository;
    private final PedidoRepository pedidoRepository;
    private final PreferenceClient preferenceClient;
    private final PaymentClient paymentClient;
    private final PedidoService pedidoService;


    public TransacaoResponseDTO criarTransacao(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado"));
        List<PreferenceItemRequest> itens = pedido.getItensPedidos().stream()
                .map(item -> PreferenceItemRequest.builder().title(item.getProduto().getNome())
                        .quantity(item.getQuantidade().intValue())
                        .unitPrice(item.getPrecoUnitario()).build()).toList();


        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://####/pedido/sucesso")
                .failure("https://####/pedido/falha")
                .pending("https://####/pedido/pendente")
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(itens)
                .externalReference(pedido.getId().toString())
                .notificationUrl("https://seuapp.com/webhooks/mercadopago")
                .backUrls(backUrls)
                .build();


        Preference preference;

        try {
            preference = this.preferenceClient.create(preferenceRequest);
        } catch (MPApiException e) {
            throw new RuntimeException("Mercado pago recusou a requisição " + e.getApiResponse());
        } catch (MPException e) {
            throw new RuntimeException("Erro de comunicacao com o Mercado Pago", e);
        }
        Transacao transacao = new Transacao(pedido, preference.getId(), preference.getInitPoint());
        transacaoRepository.save(transacao);
        return new TransacaoResponseDTO(transacao.getId(), transacao.getPagamentoUrl(), transacao.getStatusPagamento());
    }

    public void processarNotificacao(String mercadoPagoPaymentId) {
        Payment payment;
        try {
            payment = this.paymentClient.get(Long.parseLong(mercadoPagoPaymentId));
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        } catch (MPException e) {
            throw new RuntimeException(e);
        }
        Long pedidoId = Long.parseLong(payment.getExternalReference());
        Transacao transacao = transacaoRepository.findByPedidoId(pedidoId).stream().filter(t -> t.getStatusPagamento() == StatusPagamento.PENDENTE).findFirst().orElseThrow(() -> new RuntimeException("transação nao encontrada"));

        transacao.setMercadoPagoId(payment.getId().toString());

        if ("aprovado".equals(payment.getStatus())) {
            transacao.setStatusPagamento(StatusPagamento.APROVADO);
            pedidoService.marcadoComoPago(pedidoId);
        } else if ("rejeitado".equals(payment.getStatus())) {
            transacao.setStatusPagamento(StatusPagamento.REJEITADO);
            pedidoService.marcadoComoFalhou(pedidoId);
        }

        transacaoRepository.save(transacao);
    }


}
