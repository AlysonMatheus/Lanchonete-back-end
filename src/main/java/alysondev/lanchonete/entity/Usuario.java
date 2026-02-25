package alysondev.lanchonete.entity;

import alysondev.lanchonete.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "login")
    private String login;
    @Column(name = "senha")
    private String senha;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoUsuario tipo;


    public Usuario(String login, String senha, TipoUsuario tipo) {
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
    }
}
