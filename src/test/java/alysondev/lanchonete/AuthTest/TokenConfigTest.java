package alysondev.lanchonete.AuthTest;


import alysondev.lanchonete.auth.JWTUserData;
import alysondev.lanchonete.auth.TokenConfig;
import alysondev.lanchonete.entity.Cliente;
import alysondev.lanchonete.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;

@SpringBootTest
public class TokenConfigTest {

    private TokenConfig tokenConfig;

    @BeforeEach
    void setUp() {
        tokenConfig = new TokenConfig();
    }
@Test
    void deveGerarTokenValido() {

        Usuario usuario = new Usuario();
        usuario.setId(12L);
        usuario.setLogin("Alyson");



        String  token = tokenConfig.generateToken(usuario);
        assertThat(token).isNotNull();
        assertThat(token).isNotBlank();



    }
    @Test
    void deveValidarTokenGeradoComSucesso(){
        Usuario usuario = new Usuario();
        usuario.setId(12L);
        usuario.setLogin("Alyson");
        String token = tokenConfig.generateToken(usuario);

        Optional<JWTUserData> resultado = tokenConfig.validateToken(token);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().id()).isEqualTo(12L);
        assertThat(resultado.get().login()).isEqualTo("Alyson");

    }
    @Test
    void deveRetornarVazioQuandoTokenInvalido() {
        Usuario usuario = new Usuario();
        usuario.setId(12L);
        usuario.setLogin("Alyson");
        String token = tokenConfig.generateToken(usuario);

        Optional<JWTUserData> resultado = tokenConfig.validateToken("token-invalido-123");

       assertThat(resultado).isEmpty();

    }

}
