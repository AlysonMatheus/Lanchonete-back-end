package alysondev.lanchonete.ControllerTest;

import alysondev.lanchonete.controller.ClienteController;
import alysondev.lanchonete.dtos.request.ClienteRequestDTO;
import alysondev.lanchonete.dtos.response.ClienteResponseDTO;
import alysondev.lanchonete.entity.Usuario;
import alysondev.lanchonete.enums.TipoUsuario;
import alysondev.lanchonete.services.ClienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;




import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)

public class ClienteControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private ClienteController clienteController;


    @Mock
    private ClienteService clienteService;

    @Mock
    private Usuario usuario;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void CriarCliente() throws Exception {
        ClienteResponseDTO cliente = new ClienteResponseDTO(1L, "Alyson", "Alyson", "Sucesso");
        Usuario usuario = new Usuario(1L, "Alyson", "aaaa", TipoUsuario.FUNCIONARIO);

        when(clienteService.cadastrar(any(ClienteRequestDTO.class))).thenReturn(cliente);

        String jsonRequest = """
                {
                "id":1,
                "nome":"Alyson",
                "login":"Alyson",
                "mensagem": "Sucesso"
                }
                """;
        mockMvc.perform(post("/cliente/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCliente").value(1))
                .andExpect(jsonPath("$.nome").value("Alyson"))
                .andExpect(jsonPath("$.login").value("Alyson"))
                .andExpect(jsonPath("$.mensagem").value("Sucesso"));

        verify(clienteService, times(1)).cadastrar(any(ClienteRequestDTO.class));
    }


}
