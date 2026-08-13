package alysondev.lanchonete.services;


import alysondev.lanchonete.auth.RefreshToken;
import alysondev.lanchonete.auth.TokenConfig;
import alysondev.lanchonete.dtos.request.LoginRequestDTO;
import alysondev.lanchonete.dtos.response.LoginResponseDTO;
import alysondev.lanchonete.entity.Cliente;
import alysondev.lanchonete.entity.Usuario;

import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.RefreshTokenRepository;


import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Service
public class LoginService {

    private final ClienteRepository clienteRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenConfig tokenConfig;


    public LoginService(
            ClienteRepository clienteRepository,
            @Lazy AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository,
            TokenConfig tokenConfig) {

        this.clienteRepository = clienteRepository;
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenConfig = tokenConfig;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken userAndPass =
                new UsernamePasswordAuthenticationToken(loginRequestDTO.login(), loginRequestDTO.senha());


        Authentication manager = authenticationManager.authenticate(userAndPass);

        Usuario usuario = (Usuario) manager.getPrincipal();
        String token = tokenConfig.generateToken(usuario);

        String refreshToken = tokenConfig.generateRefreshToken(usuario);
        RefreshToken entidade = new RefreshToken();
        entidade.setToken(refreshToken);
        entidade.setUsuario(usuario);

        entidade.setDataExpiracao(Instant.now().plus(7, ChronoUnit.DAYS));
        refreshTokenRepository.save(entidade);

        Cliente cliente = clienteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));


        return new LoginResponseDTO(usuario.getLogin(), usuario.getTipo().name(), cliente.getId(), token, refreshToken);
    }


}
