package alysondev.lanchonete.RepositoryTest;

import alysondev.lanchonete.ContainerTestConfiguration;
import alysondev.lanchonete.entity.Cliente;
import alysondev.lanchonete.entity.Endereco;
import alysondev.lanchonete.entity.Pedido;
import alysondev.lanchonete.entity.Usuario;
import alysondev.lanchonete.enums.Pagamento;
import alysondev.lanchonete.enums.Status;
import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.EnderecoRepository;
import alysondev.lanchonete.repository.PedidoRepository;
import alysondev.lanchonete.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@DataJpaTest
@Import(ContainerTestConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PedidoRepositoryTest {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Test
    void deveEncontrarPedidosPorClienteId() {
        Usuario usuario = new Usuario();

        Cliente cliente = new Cliente();
        cliente.setCpf("46656789001");
        cliente.setNome("Alyson Matheus");
        cliente.setCelular("18998230045");
        cliente.setUsuario(usuario);
        usuarioRepository.save(usuario);
        clienteRepository.save(cliente);

        Endereco endereco = new Endereco();
        enderecoRepository.save(endereco);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setItensPedidos(List.of());
        pedido.setStatus(Status.PENDENTE);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setEndereco(endereco);
        pedido.setPrecoTotal(BigDecimal.valueOf(10));
        pedido.setPagamento(Pagamento.CREDITO);

        pedidoRepository.save(pedido);

        List<Pedido> resultado = pedidoRepository.findByClienteId(cliente.getId());
        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(pedido.getId());


    }
}
