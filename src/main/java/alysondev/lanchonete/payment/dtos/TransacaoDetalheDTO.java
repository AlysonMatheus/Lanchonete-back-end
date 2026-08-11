package alysondev.lanchonete.payment.dtos;

import alysondev.lanchonete.payment.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacaoDetalheDTO(Long id,
                                  String mercadoPagoId,
                                  BigDecimal valor,
                                  StatusPagamento status,
                                  LocalDateTime createdAt) {
}
