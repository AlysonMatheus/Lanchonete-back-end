package alysondev.lanchonete.dtos.request;

import alysondev.lanchonete.entity.Produto;
import alysondev.lanchonete.enums.Categoria;

import java.math.BigDecimal;

public record ProdutoRequestDTO(String nome, String ingredientes, Categoria categoria, BigDecimal preco, boolean ativo ) {
    public ProdutoRequestDTO (Produto produto){
        this(
                produto.getNome(),
                produto.getIngredientes(),
                produto.getCategoria(),
                produto.getPreco(),
                produto.isAtivo()
        );
    }
}
