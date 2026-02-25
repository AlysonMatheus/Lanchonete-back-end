package alysondev.lanchonete.dtos.request;

import alysondev.lanchonete.entity.ItensPedido; 
import alysondev.lanchonete.enums.Pagamento;


import java.util.List;

public record PedidoRequestDTO(Long idPedido,
                               Long idCliente,
                               Pagamento pagamento,
                               Long idEndereco,
                               List<ItensPedido> pedidoList) {
}
