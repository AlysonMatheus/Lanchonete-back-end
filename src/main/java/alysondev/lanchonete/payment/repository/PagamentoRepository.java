package alysondev.lanchonete.payment.repository;

import alysondev.lanchonete.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Payment, Long> {

}
