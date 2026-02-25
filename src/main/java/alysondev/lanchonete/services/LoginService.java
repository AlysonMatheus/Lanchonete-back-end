package alysondev.lanchonete.services;


import alysondev.lanchonete.dtos.request.LoginRequestDTO;
import alysondev.lanchonete.dtos.response.LoginResponseDTO;
import alysondev.lanchonete.entity.Cliente;
import alysondev.lanchonete.entity.Usuario;

import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class LoginService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        Usuario usuario = usuarioRepository.findByLogin(loginRequestDTO.login())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));


        if (!usuario.getSenha().equals(loginRequestDTO.senha())) {
            throw new RuntimeException("Senha incorreta");
        }


        Cliente cliente = clienteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado para este usuário"));

        String tipo = usuario.getTipo().name();


        return new LoginResponseDTO(
                usuario.getLogin(),
                tipo,
                cliente.getId()
        );
    }
}
