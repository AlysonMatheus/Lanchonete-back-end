package alysondev.lanchonete.entity;


import alysondev.lanchonete.dtos.request.EnderecoRequestDTO;
import alysondev.lanchonete.dtos.request.FuncionarioRequestDTO;
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
@Table(name = "Funcionarios")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "celular")
    private String celular;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Funcionario(FuncionarioRequestDTO funcionarioRequestDTO, Usuario usuario){
        this.nome = funcionarioRequestDTO.nome();
        this.cpf = funcionarioRequestDTO.cpf();
        this.celular = funcionarioRequestDTO.celular();
        this.usuario = usuario;
    }
    public void AtualizarDados(FuncionarioRequestDTO funcionarioRequestDTO){
        this.nome = funcionarioRequestDTO.nome();
        this.celular = funcionarioRequestDTO.celular();

    }
}
