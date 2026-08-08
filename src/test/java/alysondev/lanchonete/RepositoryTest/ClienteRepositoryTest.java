package alysondev.lanchonete.RepositoryTest;


import alysondev.lanchonete.ContainerTestConfiguration;
import alysondev.lanchonete.entity.Cliente;
import alysondev.lanchonete.entity.Usuario;
import alysondev.lanchonete.repository.ClienteRepository;
import alysondev.lanchonete.repository.EnderecoRepository;
import alysondev.lanchonete.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@Import(ContainerTestConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    @Test
    @DisplayName("Deve buscar cliente por usuario ID quando o cliente existir")
   void deveBuscarClientePorUsuarioIdComSucesso(){
        Usuario usuario = new Usuario();
        usuario.setLogin("Cristiano");
        usuario.setSenha("12345");

        Cliente cliente = new Cliente();
        cliente.setCpf("46656789001");
        cliente.setNome("Alyson Matheus");
        cliente.setCelular("18998230045");
        cliente.setUsuario(usuario);
        usuarioRepository.save(usuario);
        clienteRepository.save(cliente);

        Optional<Cliente> clienteEncontrado = clienteRepository.findByUsuarioId(usuario.getId());
          assertThat(clienteEncontrado).isPresent();
          assertThat(clienteEncontrado.get().getUsuario().getId()).isEqualTo(usuario.getId());
          assertThat(clienteEncontrado.get().getCpf()).isEqualTo("46656789001");


    }
    @Test
    void deveRetornarTrueQuandoCpfJaExiste(){
        Usuario usuario = new Usuario();
        usuario.setLogin("Cristiano");
        usuario.setSenha("12345");

        Cliente cliente = new Cliente();
        cliente.setCpf("46656789001");
        cliente.setNome("Alyson Matheus");
        cliente.setCelular("18998230045");
        cliente.setUsuario(usuario);
        usuarioRepository.save(usuario);
        clienteRepository.save(cliente);

        Boolean cpfEncontrado = clienteRepository.existsBycpf(cliente.getCpf());
        assertThat(cpfEncontrado).isTrue();

    }

}
