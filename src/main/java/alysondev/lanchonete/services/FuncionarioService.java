package alysondev.lanchonete.services;

import alysondev.lanchonete.dtos.request.FuncionarioRequestDTO;

import alysondev.lanchonete.dtos.response.FuncionarioResponseDTO;
import alysondev.lanchonete.entity.Funcionario;
import alysondev.lanchonete.entity.Usuario;
import alysondev.lanchonete.repository.FuncionarioRepository;
import alysondev.lanchonete.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final UsuarioRepository usuarioRepository;
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioResponseDTO cadastrar(FuncionarioRequestDTO funcionarioRequestDTO) {
        Usuario usuario = new Usuario(funcionarioRequestDTO.login(), funcionarioRequestDTO.senha(), "ADMIN");
        usuarioRepository.save(usuario);
        Funcionario funcionario = new Funcionario(funcionarioRequestDTO, usuario);
        funcionarioRepository.save(funcionario);
        return new FuncionarioResponseDTO(funcionario);
    }

    public FuncionarioResponseDTO editar(Long id,FuncionarioRequestDTO funcionarioRequestDTO) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Funcionario nao encontrado"));
        funcionario.AtualizarDados(funcionarioRequestDTO);


        funcionarioRepository.save(funcionario);

        return new FuncionarioResponseDTO(funcionario);
    }

}
