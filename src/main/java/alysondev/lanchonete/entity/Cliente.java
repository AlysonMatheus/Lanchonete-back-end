package alysondev.lanchonete.entity;

import alysondev.lanchonete.dtos.request.ClienteRequestDTO;
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
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "celular")
    private String celular;


    public Cliente(ClienteRequestDTO clienteRequestDTO, Usuario usuario) {
        this.nome = clienteRequestDTO.nome();
        this.cpf = clienteRequestDTO.cpf();
        this.celular = clienteRequestDTO.celular();
        this.usuario = usuario;
    }
    public void AtualizarDados(ClienteRequestDTO clienteRequestDTO){
        this.nome = clienteRequestDTO.nome();
        this.celular = clienteRequestDTO.celular();
    }
}
