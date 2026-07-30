package alysondev.lanchonete.ServiceTest;

import alysondev.lanchonete.entity.Pedido;
import alysondev.lanchonete.enums.Status;
import alysondev.lanchonete.execption.PedidoNaoEncontradoException;
import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.EnderecoRepository;
import alysondev.lanchonete.repository.PedidoRepository;
import alysondev.lanchonete.repository.ProdutoRepository;
import alysondev.lanchonete.services.PedidoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

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

    void deveEncontrarPedidoQuandoIdExiste(Long Id) {


    }

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
    void deveLancarExcecaoQuandoPedidoNaoEncontrado(){

        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(PedidoNaoEncontradoException.class,()-> pedidoService.cancelarPedido(99L));

        verify(pedidoRepository, never()).delete(any());
    }
}
