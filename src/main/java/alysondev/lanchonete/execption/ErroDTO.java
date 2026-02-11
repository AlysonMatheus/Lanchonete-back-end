package alysondev.lanchonete.execption;

import java.time.LocalDateTime;

public record ErroDTO(String mensagem, LocalDateTime hora) {
}
