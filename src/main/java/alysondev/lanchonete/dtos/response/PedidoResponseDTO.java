package alysondev.lanchonete.dtos.response;



import alysondev.lanchonete.entity.Pedido;
import alysondev.lanchonete.enums.Pagamento;
import alysondev.lanchonete.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;

public record PedidoResponseDTO(
        Long idPedido,
        Long idCliente,
        Pagamento pagamento,
        BigDecimal precoTotal,
        Status status,
        LocalDateTime dataHora,
        EnderecoResponseDTO endereco,
        List<ItemPedidoResponseDTO> pedidoList
) {
    public PedidoResponseDTO(Pedido pedido){
        this(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getPagamento(),
                pedido.getPrecoTotal(),
                pedido.getStatus(),
                pedido.getDataHora(),
                new EnderecoResponseDTO(pedido.getEndereco()),
                pedido.getItensPedidos().stream()
                        .map(ItemPedidoResponseDTO::new)
                        .toList()
        );
    }
}
