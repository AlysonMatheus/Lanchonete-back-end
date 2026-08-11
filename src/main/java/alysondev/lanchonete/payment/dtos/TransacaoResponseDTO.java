package alysondev.lanchonete.payment.dtos;

import alysondev.lanchonete.payment.StatusPagamento;

public record TransacaoResponseDTO(Long transacaoId, String checoutUrl, StatusPagamento status) {

}
