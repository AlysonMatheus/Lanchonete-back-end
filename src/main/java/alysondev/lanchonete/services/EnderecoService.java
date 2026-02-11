package alysondev.lanchonete.services;

import alysondev.lanchonete.dtos.request.EnderecoRequestDTO;
import alysondev.lanchonete.dtos.response.EnderecoResponseDTO;
import alysondev.lanchonete.entity.Cliente;
import alysondev.lanchonete.entity.Endereco;
import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;

    public EnderecoResponseDTO adicionar(EnderecoRequestDTO enderecoRequestDTO) {
        Cliente cliente = clienteRepository.findById(enderecoRequestDTO.idcliente()).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Endereco endereco = new Endereco(enderecoRequestDTO, cliente);
        enderecoRepository.save(endereco);

        return new EnderecoResponseDTO(endereco);
    }

    public List<EnderecoResponseDTO> listarTodos() {
        List<Endereco> lista = enderecoRepository.findAll();
        return lista.stream().map(endereco -> new EnderecoResponseDTO(endereco)).collect(Collectors.toList());

    }

    public EnderecoResponseDTO listarporid(Long id) {
        Endereco lista = enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException("Endereco não econtrado"));
        return new EnderecoResponseDTO(lista);

    }

    public void excluir(Long id) {
        Endereco endereco = enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException("Endereco não encontrado"));

        enderecoRepository.delete(endereco);
    }


}
