package alysondev.lanchonete.dtos.response;

import alysondev.lanchonete.entity.Cliente;

import java.util.List;

public record ClienteResponseDTO(Long id, String nome, String login, String mensagem
 ) {
    public ClienteResponseDTO(Cliente cliente){
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getUsuario().getLogin(),

                "Sucesso ao cadastrar!"
        );
    }
}
