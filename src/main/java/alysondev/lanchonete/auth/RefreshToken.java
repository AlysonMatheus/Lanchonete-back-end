package alysondev.lanchonete.auth;

import alysondev.lanchonete.entity.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@Entity()
@Table(name = "refresh_Token")
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "token")
    private String token;
    @ManyToOne
    @Column(name = "id_usuario")
    private Usuario usuario;
     @Column(name = "data_expiração")
    private Instant dataExpiracao;
}
