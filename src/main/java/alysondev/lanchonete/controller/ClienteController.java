package alysondev.lanchonete.controller;

import alysondev.lanchonete.dtos.request.ClienteRequestDTO;
import alysondev.lanchonete.dtos.response.ClienteResponseDTO;
import alysondev.lanchonete.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    private ResponseEntity<ClienteResponseDTO> cadastrar(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        var cliente = clienteService.cadastrar(clienteRequestDTO);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/editar/{id}")
    private ResponseEntity<ClienteResponseDTO> editar(@Valid @PathVariable @RequestBody Long id, ClienteRequestDTO clienteRequestDTO) {
        var cliente = clienteService.editar(id, clienteRequestDTO);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    private ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        List<ClienteResponseDTO> listar = clienteService.listar();
        return ResponseEntity.ok(listar);
    }
}
