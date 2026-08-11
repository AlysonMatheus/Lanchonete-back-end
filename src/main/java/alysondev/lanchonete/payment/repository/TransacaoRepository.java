package alysondev.lanchonete.payment.repository;

import alysondev.lanchonete.payment.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {


    List<Transacao> findByPedidoId(Long pedidoId);
    Optional<Transacao>findByMercadoPagoId(String mercadoPagoId);
}
