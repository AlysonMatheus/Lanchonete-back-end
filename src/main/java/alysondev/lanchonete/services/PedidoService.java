package alysondev.lanchonete.services;


import alysondev.lanchonete.dtos.request.PedidoRequestDTO;
import alysondev.lanchonete.dtos.response.PedidoResponseDTO;
import alysondev.lanchonete.entity.*;
import alysondev.lanchonete.enums.Status;
import alysondev.lanchonete.execption.ClienteNaoEncontradoException;
import alysondev.lanchonete.execption.PedidoNaoEncontradoException;
import alysondev.lanchonete.execption.ProdutoNaoEncontradoException;
import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.EnderecoRepository;
import alysondev.lanchonete.repository.PedidoRepository;

import alysondev.lanchonete.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class PedidoService {


    private final PedidoRepository pedidoRepository;

    private final ProdutoRepository produtoRepository;

    private final ClienteRepository clienteRepository;
    private  final EnderecoRepository enderecoRepository;



    public PedidoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO) throws RuntimeException {



        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.idCliente()).orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));

        List<ItensPedido> itens = pedidoRequestDTO.pedidoList().stream().map(itemDTO -> {
            Produto produto = produtoRepository.findById(itemDTO.getId()).orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));
            return new ItensPedido(produto, itemDTO);
        }).toList();
        Endereco endereco = enderecoRepository.findById(pedidoRequestDTO.idEndereco()).orElseThrow(()-> new RuntimeException("Endereco nao encontrado"));
        Pedido pedido = new Pedido(cliente, pedidoRequestDTO, itens, endereco);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return new PedidoResponseDTO(pedidoSalvo);

    }
    @Transactional

    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado"));
        if (pedido.getStatus() == Status.PENDENTE) {
            pedidoRepository.delete(pedido);
        }
        if (pedido.getStatus() == Status.CONFIRMADO) {
            throw new IllegalArgumentException("Pedido não pode ser cancelado nesse estado");
        }
    }

    public List<PedidoResponseDTO> listarPedidosCliente(Long id) {
        List<Pedido> pedido = pedidoRepository.findByClienteId(id);
        return pedido.stream()
                .map(PedidoResponseDTO::new).toList();

    }
    public List<PedidoResponseDTO>listarTodos(){
        List<Pedido> pedidos = pedidoRepository.findAll();
        return  pedidos.stream().map(PedidoResponseDTO::new).toList();
    }


}





