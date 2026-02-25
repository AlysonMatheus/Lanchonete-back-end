package alysondev.lanchonete.controller;

import alysondev.lanchonete.dtos.request.PedidoRequestDTO;
import alysondev.lanchonete.dtos.response.PedidoResponseDTO;

import alysondev.lanchonete.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/pedido")

public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/adicionar")
    public ResponseEntity<PedidoResponseDTO> adicionar(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        var pedido = pedidoService.criarPedido(pedidoRequestDTO);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping("/cancelar")
    public void cancelar(@RequestBody Long id) {
        pedidoService.cancelarPedido(id);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorCliente(@PathVariable Long id) {
        var buscar = pedidoService.listarPedidosCliente(id);

        return ResponseEntity.ok(buscar);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        var listar = pedidoService.listarTodos();
        return ResponseEntity.ok(listar);
    }
}
