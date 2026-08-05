package alysondev.lanchonete.ServiceTest;

import alysondev.lanchonete.dtos.request.PedidoRequestDTO;
import alysondev.lanchonete.dtos.request.ProdutoRequestDTO;
import alysondev.lanchonete.dtos.response.ItemPedidoResponseDTO;
import alysondev.lanchonete.dtos.response.PedidoResponseDTO;
import alysondev.lanchonete.entity.*;
import alysondev.lanchonete.enums.Categoria;
import alysondev.lanchonete.enums.Pagamento;
import alysondev.lanchonete.enums.Status;
import alysondev.lanchonete.execption.ClienteNaoEncontradoException;
import alysondev.lanchonete.execption.PedidoNaoEncontradoException;
import alysondev.lanchonete.execption.ProdutoNaoEncontradoException;
import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.EnderecoRepository;
import alysondev.lanchonete.repository.PedidoRepository;
import alysondev.lanchonete.repository.ProdutoRepository;
import alysondev.lanchonete.services.PedidoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {


    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private PedidoService pedidoService;


    @Test
    void deveDeletarPedidoQuandoStatusPendente() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(Status.PENDENTE);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        pedidoService.cancelarPedido(1L);

        verify(pedidoRepository, times(1)).delete(pedido);
    }

    @Test
    void deveLancarExcecaoQuandoStatusConfirmado() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(Status.CONFIRMADO);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        assertThrows(IllegalArgumentException.class, () -> pedidoService.cancelarPedido(1L));

        verify(pedidoRepository, never()).delete(pedido);
    }

    @Test
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() {

        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(PedidoNaoEncontradoException.class, () -> pedidoService.cancelarPedido(99L));

        verify(pedidoRepository, never()).delete(any());
    }
    //-----------------------------------

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO(55L, 22L, Pagamento.PIX, 25L, List.of());
        when(clienteRepository.findById(22L)).thenReturn(Optional.empty());
        assertThrows(ClienteNaoEncontradoException.class, () -> pedidoService.criarPedido(pedidoRequestDTO));


    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        ItensPedido item = new ItensPedido();
        item.setId(7L);
        Cliente clienteFake = new Cliente();
        clienteFake.setId(22L);

        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO(55L, 22L, Pagamento.PIX, 25L, List.of(item));
        when(clienteRepository.findById(22L)).thenReturn(Optional.of(clienteFake));
        when(produtoRepository.findById(7L)).thenReturn(Optional.empty());
        assertThrows(ProdutoNaoEncontradoException.class, () -> pedidoService.criarPedido(pedidoRequestDTO));

        verify(pedidoRepository, never()).save(any());

    }

    @Test
    void deveLancarExececaoQuandoEnderecoNaoExncontrado() {


        Produto produtoFake = new Produto();
        produtoFake.setPreco(BigDecimal.valueOf(10));
        ItensPedido item = new ItensPedido();
        item.setId(7L);
        Cliente clienteFake = new Cliente();
        clienteFake.setId(22L);



        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO(55L, 22L, Pagamento.PIX, 25L, List.of(item));
        when(clienteRepository.findById(22L)).thenReturn(Optional.of(clienteFake));
        when(produtoRepository.findById(7L)).thenReturn(Optional.of(produtoFake));
        when(enderecoRepository.findById(25L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> pedidoService.criarPedido(pedidoRequestDTO));
        verify(pedidoRepository, never()).save(any());

    }

    @Test
    void criarPedidocomSucesso() {
        Produto produtoFake = new Produto();
        produtoFake.setPreco(BigDecimal.valueOf(10));

        ItensPedido item = new ItensPedido();
        item.setId(7L);
        item.setQuantidade(10.0);
        item.setPrecoUnitario(BigDecimal.valueOf(25));
        item.setProduto(produtoFake);


        Cliente clienteFake = new Cliente();
        clienteFake.setId(22L);


        Endereco endereco = new Endereco();
        endereco.setId(55L);
        endereco.setCliente(clienteFake);

        PedidoRequestDTO pedidoRequestDTO = new PedidoRequestDTO(55L, 22L, Pagamento.PIX, 25L, List.of(item));
        Pedido pedidoSalvoFke = new Pedido(clienteFake, pedidoRequestDTO, List.of(item), endereco);
        pedidoSalvoFke.setId(55L);

        when(clienteRepository.findById(22L)).thenReturn(Optional.of(clienteFake));
        when(produtoRepository.findById(7L)).thenReturn(Optional.of(produtoFake));
        when(enderecoRepository.findById(25L)).thenReturn(Optional.of(endereco));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoSalvoFke);

        PedidoResponseDTO resultado = pedidoService.criarPedido(pedidoRequestDTO);

        assertNotNull(resultado);

    }
    @Test
    void avancarStatus(){
        Cliente clienteFake = new Cliente();
        clienteFake.setId(22L);

        Pedido pedido = new Pedido();
        pedido.setId(10L);
        pedido.setCliente(clienteFake);
        pedido.setItensPedidos(List.of());

        pedido.setStatus(Status.PENDENTE);

        when(pedidoRepository.findById(10L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        ArgumentCaptor<Pedido> status = ArgumentCaptor.forClass(Pedido.class);
        pedidoService.avancarStatus(10L);
        verify(pedidoRepository).save(status.capture());
        assertEquals(Status.CONFIRMADO, status.getValue().getStatus());

    }
    @Test
    void  deveListarTodosOsPedido(){
        Cliente clienteFake = new Cliente();
        clienteFake.setId(22L);

        Pedido pedido = new Pedido();
        pedido.setId(10L);
        pedido.setCliente(clienteFake);
        pedido.setItensPedidos(List.of());

        List<Pedido> pedidos =List.of(pedido);
        when(pedidoRepository.findAll()).thenReturn(pedidos);
        List<PedidoResponseDTO> resultado = pedidoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pedidoRepository, times(1)).findAll();

    }
    @Test
    void deveListarPedidosPorCliente(){
        Cliente clienteFake = new Cliente();
        clienteFake.setId(22L);

        Pedido pedido = new Pedido();
        pedido.setId(10L);
        pedido.setCliente(clienteFake);
        pedido.setItensPedidos(List.of());

        List<Pedido> pedidos =List.of(pedido);
        when(pedidoRepository.findByClienteId(22L)).thenReturn(pedidos);

        List<PedidoResponseDTO> resultado = pedidoService.listarPedidosCliente(22L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pedidoRepository, times(1)).findByClienteId(22L);
    }

}
