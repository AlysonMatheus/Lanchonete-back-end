package alysondev.lanchonete.dtos.response;

public record LoginResponseDTO(
        String nome,
        String tipo,
        Long idOrigem
) {}