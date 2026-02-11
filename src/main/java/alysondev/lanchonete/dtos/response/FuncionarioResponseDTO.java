package alysondev.lanchonete.dtos.response;

import alysondev.lanchonete.entity.Funcionario;

public record FuncionarioResponseDTO(Long id ,String nome, String login, String mensagem) {
    public FuncionarioResponseDTO(Funcionario funcionario) {
        this(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getUsuario().getLogin(),
                "Sucesso ao cadastrar!"
        );
    }
}
