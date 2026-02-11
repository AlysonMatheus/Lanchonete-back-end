package alysondev.lanchonete.dtos.request;

import alysondev.lanchonete.entity.Endereco;

public record EnderecoRequestDTO (Long id, String rua, String bairro, String cidade, String numero, Long idcliente){
    public EnderecoRequestDTO(Endereco endereco) {
        this(
        endereco.getId(),
        endereco.getRua(),
        endereco.getBairro(),
        endereco.getCidade(),
        endereco.getNumero(),
        endereco.getCliente().getId()


    );}
}
