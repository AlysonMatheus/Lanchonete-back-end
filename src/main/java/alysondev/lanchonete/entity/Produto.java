package alysondev.lanchonete.entity;

import alysondev.lanchonete.dtos.request.ProdutoRequestDTO;
import alysondev.lanchonete.enums.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "ingredientes")
    private String ingredientes;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Categoria categoria;

    @Column(name = "preco")
    private BigDecimal preco;
    @Column(name = "ativo", nullable = false)
    private Boolean  ativo = true;


public Produto(ProdutoRequestDTO produtoRequestDTO){
    this.nome = produtoRequestDTO.nome();
    this.ingredientes = produtoRequestDTO.ingredientes();
    this.categoria = produtoRequestDTO.categoria();
    this.preco = produtoRequestDTO.preco();
    this.ativo = produtoRequestDTO.ativo();

}
    public void atualizarDados(ProdutoRequestDTO produtoRequestDTO) {
        this.nome = produtoRequestDTO.nome();
        this.categoria = produtoRequestDTO.categoria();
        this.ingredientes = produtoRequestDTO.ingredientes();
        this.preco = produtoRequestDTO.preco();
        this.ativo = produtoRequestDTO.ativo();
    }

}
