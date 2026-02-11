package alysondev.lanchonete.dtos.response;

import alysondev.lanchonete.entity.Produto;
import alysondev.lanchonete.enums.Categoria;

import java.math.BigDecimal;

public record ProdutoResponseDTO( Long id ,String nome, Categoria categoria, String ingredientes, BigDecimal preco, boolean ativo) {
    public ProdutoResponseDTO(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getIngredientes(),
                produto.getPreco(),
                produto.isAtivo()
        );

    }
}
