package alysondev.lanchonete.dtos.response;

import alysondev.lanchonete.entity.Endereco;

public record EnderecoResponseDTO( Long id ,String rua, String bairro, String cidade, String numero,Long idcliente) {
    public EnderecoResponseDTO(Endereco endereco){
        this(endereco.getId(),
                endereco.getRua(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getNumero(),
                endereco.getCliente().getId()
        );
    }
}
