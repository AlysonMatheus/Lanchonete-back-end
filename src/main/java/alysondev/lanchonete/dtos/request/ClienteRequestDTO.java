package alysondev.lanchonete.dtos.request;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



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
