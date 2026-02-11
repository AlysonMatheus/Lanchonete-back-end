package alysondev.lanchonete.dtos.response;



import alysondev.lanchonete.entity.Pedido;
import alysondev.lanchonete.enums.Pagamento;
import alysondev.lanchonete.enums.Status;

import java.time.LocalDateTime;

import java.util.List;

public record PedidoResponseDTO(Long id, Pagamento pagamento, java.math.BigDecimal precoTotal, Status status, LocalDateTime dataHora, List<ItemPedidoResponseDTO> pedidoList) {
    public PedidoResponseDTO (Pedido pedido){
        this(   pedido.getId(),
                pedido.getPagamento(),
                pedido.getPrecoTotal(),
                pedido.getStatus(),
                pedido.getDataHora(),
                pedido.getItensPedidos().stream().map(ItemPedidoResponseDTO::new ).toList());
    }
}
