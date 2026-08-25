package alysondev.lanchonete.AuthTest;


import alysondev.lanchonete.auth.JWTUserData;
import alysondev.lanchonete.auth.TokenConfig;
import alysondev.lanchonete.entity.Cliente;
import alysondev.lanchonete.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)

public class TokenConfigTest {

    private TokenConfig tokenConfig;

    @BeforeEach
    void setUp() {
        tokenConfig = new TokenConfig();
        ReflectionTestUtils.setField(tokenConfig, "secret", "secret-de-test");
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
        Optional<JWTUserData> resultado = tokenConfig.validateToken("token-invalido-123");
        assertThat(resultado).isEmpty();

    }

}
