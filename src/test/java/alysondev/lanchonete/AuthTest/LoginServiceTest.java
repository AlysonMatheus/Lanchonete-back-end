package alysondev.lanchonete.AuthTest;

import alysondev.lanchonete.auth.JWTUserData;
import alysondev.lanchonete.auth.TokenConfig;
import alysondev.lanchonete.dtos.request.LoginRequestDTO;
import alysondev.lanchonete.dtos.response.LoginResponseDTO;
import alysondev.lanchonete.entity.Cliente;
import alysondev.lanchonete.entity.Usuario;
import alysondev.lanchonete.enums.TipoUsuario;
import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.UsuarioRepository;
import alysondev.lanchonete.services.LoginService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LoginServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenConfig tokenConfig;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ClienteRepository clienteRepository;


    @InjectMocks
    private LoginService loginService;


    @Test
    void deveRetornarTokenQuandoCredenciaisValidas() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin("Cristiano");
        usuario.setSenha("12345");
        usuario.setTipo(TipoUsuario.CLIENTE);
        Authentication authenticationFake = new UsernamePasswordAuthenticationToken(usuario, null, null);

        Cliente cliente = new Cliente();
        cliente.setId(99L);

        when(authenticationManager.authenticate(any())).thenReturn(authenticationFake);
        when(tokenConfig.generateToken(usuario)).thenReturn("token-fake-123");
        when(clienteRepository.findByUsuarioId(usuario.getId())).thenReturn(Optional.of(cliente));

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("Cristiano", "12345");

        LoginResponseDTO resultado = loginService.login(loginRequestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.token()).isEqualTo("token-fake-123");
        assertThat(resultado.idOrigem()).isEqualTo(99L);
    }

    @Test
    void deveLancarExcecaoQuandoCredenciaisInvalidas() {
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Credenciais Invalidas"));

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("Cristiano", "123456");

        assertThrows(BadCredentialsException.class, () -> loginService.login(loginRequestDTO));


    }

}
