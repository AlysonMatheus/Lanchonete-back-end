package alysondev.lanchonete.services;


import alysondev.lanchonete.entity.Pedido;
import alysondev.lanchonete.execption.PedidoNaoEncontradoException;
import alysondev.lanchonete.payment.Transacao;
import alysondev.lanchonete.payment.dtos.TransacaoDetalheDTO;
import alysondev.lanchonete.payment.dtos.TransacaoResponseDTO;
import alysondev.lanchonete.payment.repository.PagamentoRepository;
import alysondev.lanchonete.payment.repository.TransacaoRepository;
import alysondev.lanchonete.repository.PedidoRepository;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final TransacaoRepository transacaoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;

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
        PreferenceClient client = new PreferenceClient();

        Preference preference;
        try {
        preference = client.create(preferenceRequest);
        } catch (MPApiException e) {
            throw new RuntimeException("Mercado pago recusou a requisição " + e.getApiResponse());
        } catch (MPException e) {
            throw new RuntimeException("Erro de comunicacao com o Mercado Pago", e);
        }
        Transacao transacao = new Transacao(pedido, preference.getId(), preference.getInitPoint());
        transacaoRepository.save(transacao);
        return new TransacaoResponseDTO(transacao.getId(), transacao.getPagamentoUrl(), transacao.getStatusPagamento());
    }


}
