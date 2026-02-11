package alysondev.lanchonete.entity;

import alysondev.lanchonete.dtos.response.ItemPedidoResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itens_pedido")
public class ItensPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade")
    private Double quantidade;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    public ItensPedido(Produto produto, ItensPedido itensPedido) {


        this.setProduto(produto);
        this.setQuantidade(itensPedido.getQuantidade());
        this.setPrecoUnitario(produto.getPreco());
        this.setPedido(pedido);


    }
}
