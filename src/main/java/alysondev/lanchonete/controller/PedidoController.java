package alysondev.lanchonete.controller;

import alysondev.lanchonete.dtos.request.PedidoRequestDTO;
import alysondev.lanchonete.dtos.response.PedidoResponseDTO;

import alysondev.lanchonete.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")

public class PedidoController {
@Autowired
    private PedidoService pedidoService;

    @PostMapping("/adicionar")
    private ResponseEntity<PedidoResponseDTO> adicionar(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        var pedido = pedidoService.criarPedido(pedidoRequestDTO);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping("/cancelar")
    private void cancelar(@RequestBody Long id) {
        pedidoService.cancelarPedido(id);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorCliente(@RequestBody @RequestParam Long id) {
        var buscar = pedidoService.listarPedidosCliente(id);

        return ResponseEntity.ok(buscar);
    }
}
