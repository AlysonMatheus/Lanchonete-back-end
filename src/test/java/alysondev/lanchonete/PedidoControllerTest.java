package alysondev.lanchonete;

import alysondev.lanchonete.controller.PedidoController;

import alysondev.lanchonete.dtos.request.PedidoRequestDTO;
import alysondev.lanchonete.dtos.response.EnderecoResponseDTO;

import alysondev.lanchonete.dtos.response.PedidoResponseDTO;

import alysondev.lanchonete.enums.Pagamento;
import alysondev.lanchonete.enums.Status;

import alysondev.lanchonete.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.http.MediaType;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// Estáticos do Mockito
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoService pedidoService; // Único mock necessário para o Controller

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void CriarPedido() throws Exception {

        EnderecoResponseDTO enderecoRes = new EnderecoResponseDTO(10L, "Rua das Flores", "Bairro X", "Cidade Y", "123", 1L);

        PedidoResponseDTO responseDTO = new PedidoResponseDTO(
                100L,
                1L,
                "Alyson",
                Pagamento.PIX,
                new BigDecimal("50.00"),
                Status.PENDENTE,
                LocalDateTime.now(),
                enderecoRes,
                List.of()
        );


        when(pedidoService.criarPedido(any(PedidoRequestDTO.class))).thenReturn(responseDTO);


        String jsonRequest = """
                {
                    "idCliente": 1,
                    "idEndereco": 10,
                    "pagamento": "PIX",
                    "pedidoList": []
                }
                """;

        mockMvc.perform(post("/pedido/adicionar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(100))
                .andExpect(jsonPath("$.endereco.rua").value("Rua das Flores"))
                .andExpect(jsonPath("$.endereco.idcliente").value(1));


        verify(pedidoService, times(1)).criarPedido(any(PedidoRequestDTO.class));
    }
}