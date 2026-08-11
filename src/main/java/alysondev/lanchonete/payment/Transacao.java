package alysondev.lanchonete.payment;

import alysondev.lanchonete.entity.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transacao")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(name = "mercado_pago_id")
    private String mercadoPagoId;

    @Column(name = "pagamento_url")
    private String pagamentoUrl;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento", nullable = false)
    private StatusPagamento statusPagamento;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected void  onCreate(){
        this.createdAt =LocalDateTime.now();
        if (this.statusPagamento == null){
            this.statusPagamento = StatusPagamento.PENDENTE;
        }
    }
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
    public Transacao(Pedido pedido, String mercadoPagoId, String pagamentoUrl) {
        this.pedido = pedido;
        this.valor = pedido.getPrecoTotal();
        this.mercadoPagoId = mercadoPagoId;
        this.pagamentoUrl = pagamentoUrl;
    }
}
