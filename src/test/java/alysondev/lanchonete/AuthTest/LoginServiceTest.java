package alysondev.lanchonete.AuthTest;

import alysondev.lanchonete.auth.*;
import alysondev.lanchonete.dtos.request.LoginRequestDTO;
import alysondev.lanchonete.dtos.response.LoginResponseDTO;
import alysondev.lanchonete.entity.Cliente;
import alysondev.lanchonete.entity.Usuario;
import alysondev.lanchonete.enums.TipoUsuario;
import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.RefreshTokenRepository;
import alysondev.lanchonete.repository.UsuarioRepository;
import alysondev.lanchonete.services.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

public class LoginServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenConfig tokenConfig;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;


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

    @Test
    void deveRetornarNovoAccessTokenQuandoRefreshTokenForValido() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin("Alyson");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsuario(usuario);
        refreshToken.setDataExpiracao(Instant.now().plus(7, ChronoUnit.DAYS));
        refreshToken.setToken("refresh-token-valido-123");


        when(refreshTokenRepository.findByToken("refresh-token-valido-123")).thenReturn(Optional.of(refreshToken));
        when(tokenConfig.generateToken(usuario)).thenReturn("novo-access-token");

            RefreshResponseDTO responseDTO = loginService.generateRefreshToken("refresh-token-valido-123");
        assertThat(responseDTO.token()).isEqualTo("novo-access-token");



    }
    @Test
    void deveLancarExcecaoQuandoRefreshTokenNaoEncontrado(){
        when(refreshTokenRepository.findByToken("123")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,()->loginService.generateRefreshToken("123"));
    }

    @Test
    void deveLancarExcecaoQuandoRefreshTokenExpirado(){
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin("Alyson");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsuario(usuario);
        refreshToken.setDataExpiracao(Instant.now().minus(1, ChronoUnit.DAYS));
        refreshToken.setToken("refresh-token-valido-123");


        when(refreshTokenRepository.findByToken("refresh-token-valido-123")).thenReturn(Optional.of(refreshToken));


      assertThrows(RuntimeException.class,()-> loginService.generateRefreshToken("refresh-token-valido-123"));

        verify(refreshTokenRepository,times(1)).delete(refreshToken);
    }

}
