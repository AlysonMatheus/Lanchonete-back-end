package alysondev.lanchonete.services;


import alysondev.lanchonete.dtos.request.ClienteRequestDTO;
import alysondev.lanchonete.dtos.response.ClienteResponseDTO;
import alysondev.lanchonete.entity.Cliente;
import alysondev.lanchonete.entity.Usuario;
import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {


    private final UsuarioRepository usuarioRepository;


    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteResponseDTO cadastrar(ClienteRequestDTO clienteRequestDTO) {
        Usuario usuario = new Usuario(clienteRequestDTO.login(), clienteRequestDTO.senha(), "CLIENTE");
        usuarioRepository.save(usuario);
        Cliente cliente = new Cliente(clienteRequestDTO, usuario);
        clienteRepository.save(cliente);

        return new ClienteResponseDTO(cliente);

    }

    @Transactional
    public ClienteResponseDTO editar(Long id, ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não econtrado"));
        cliente.AtualizarDados(clienteRequestDTO);
        clienteRepository.save(cliente);

        return new ClienteResponseDTO(cliente);
    }
    public List<ClienteResponseDTO> listar(){
          List<Cliente> clientes  = clienteRepository.findAll();
          return clientes.stream().map(cliente -> new ClienteResponseDTO(cliente)).collect(Collectors.toList());
    }

}