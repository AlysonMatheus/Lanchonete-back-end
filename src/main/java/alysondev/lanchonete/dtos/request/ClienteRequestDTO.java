package alysondev.lanchonete.dtos.request;


import alysondev.lanchonete.entity.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ClienteRequestDTO(
        @NotBlank(message = "Nome é obrigatorio")
        String nome,
        @NotBlank(message = "Cpf é obrigatorio")
        @Size (min = 11, max = 11)
        String cpf,
        String celular,
        @NotBlank(message = "Login é obrigatorio")
        String login,
        String senha
        ) {


}
