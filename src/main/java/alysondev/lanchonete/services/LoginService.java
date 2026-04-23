package alysondev.lanchonete.services;


import alysondev.lanchonete.auth.TokenConfig;
import alysondev.lanchonete.dtos.request.LoginRequestDTO;
import alysondev.lanchonete.dtos.response.LoginResponseDTO;
import alysondev.lanchonete.entity.Cliente;
import alysondev.lanchonete.entity.Usuario;

import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.UsuarioRepository;



import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import org.springframework.stereotype.Service;



@Service
public class LoginService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;


    public LoginService(UsuarioRepository usuarioRepository,
                        ClienteRepository clienteRepository,
                        @Lazy AuthenticationManager authenticationManager,
                        TokenConfig tokenConfig) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken userAndPass =
                new UsernamePasswordAuthenticationToken(loginRequestDTO.login(), loginRequestDTO.senha());


        Authentication manager = authenticationManager.authenticate(userAndPass);

        Usuario usuario = (Usuario) manager.getPrincipal();
        String token = tokenConfig.generateToken(usuario);
        Cliente cliente = clienteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return new LoginResponseDTO(usuario.getLogin(), usuario.getTipo().name(), cliente.getId(), token);
    }
}
