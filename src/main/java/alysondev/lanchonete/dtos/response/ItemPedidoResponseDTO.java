package alysondev.lanchonete.dtos.response;

import alysondev.lanchonete.entity.ItensPedido;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        String nomeProduto,
        Double quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
    public ItemPedidoResponseDTO(ItensPedido itensPedido) {
        this(  itensPedido.getProduto().getNome(),
                itensPedido.getQuantidade(),
                itensPedido.getPrecoUnitario(),
                itensPedido.getPrecoUnitario().multiply(BigDecimal.valueOf(itensPedido.getQuantidade())));
    }
}
