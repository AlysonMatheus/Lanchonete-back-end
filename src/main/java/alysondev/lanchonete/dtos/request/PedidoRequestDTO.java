package alysondev.lanchonete.dtos.request;

import alysondev.lanchonete.entity.ItensPedido;
import alysondev.lanchonete.entity.Produto;
import alysondev.lanchonete.enums.Pagamento;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;

public record PedidoRequestDTO(Long id,
                               Pagamento pagamento,
                               Double precoTotal,
                               Date dataHora,
                               Produto produto,
                               @NotBlank(message = "Lista esta vazia")
                               List<ItensPedido> pedidoList) {
}
