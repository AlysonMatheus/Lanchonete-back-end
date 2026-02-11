package alysondev.lanchonete.entity;

import alysondev.lanchonete.dtos.request.PedidoRequestDTO;
import alysondev.lanchonete.enums.Pagamento;
import alysondev.lanchonete.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItensPedido> itensPedidos;

    @Column(name = "preco_total")
    private BigDecimal precoTotal;
    @Enumerated(EnumType.STRING)
    @Column(name = "pagamento")
    private Pagamento pagamento;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    public Pedido(Cliente cliente, PedidoRequestDTO pedidoRequestDTO, List<ItensPedido> itensPedidos) {

        this.setCliente(cliente);
        this.setPagamento(pedidoRequestDTO.pagamento());
        this.setDataHora(LocalDateTime.now());
        this.setItensPedidos(itensPedidos);
        this.setStatus(Status.PENDENTE);

        itensPedidos.stream().forEach(i -> i.setPedido(this));
        BigDecimal total = itensPedidos.stream().map(i -> i.getPrecoUnitario().multiply(BigDecimal.valueOf(i.getQuantidade()))).reduce(BigDecimal.ZERO, BigDecimal::add);

        this.setPrecoTotal(total);

    }
}
