package alysondev.lanchonete.dtos.request;

import alysondev.lanchonete.entity.ItensPedido;
import alysondev.lanchonete.entity.Produto;
import alysondev.lanchonete.enums.Pagamento;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;

public record PedidoRequestDTO(Long idPedido,
                               Long idCliente,
                               Pagamento pagamento,
                               Double precoTotal,
                               Date dataHora,
                               Long idEndereco,

                               List<ItensPedido> pedidoList) {
}
