package alysondev.lanchonete.entity;

import alysondev.lanchonete.dtos.request.EnderecoRequestDTO;
import alysondev.lanchonete.dtos.response.EnderecoResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rua")
    private String rua;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "numero")
    private String numero;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    public Endereco(EnderecoRequestDTO enderecoResponseDTO, Cliente cliente) {
        this.rua = enderecoResponseDTO.rua();
        this.bairro = enderecoResponseDTO.bairro();
        this.cidade = enderecoResponseDTO.cidade();
        this.numero = enderecoResponseDTO.numero();
        this.cliente = cliente;

    }
}
