package alysondev.lanchonete.controller;

import alysondev.lanchonete.dtos.request.ClienteRequestDTO;
import alysondev.lanchonete.dtos.request.EnderecoRequestDTO;
import alysondev.lanchonete.dtos.response.EnderecoResponseDTO;

import alysondev.lanchonete.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;


    @PostMapping("/adicionar")
    private ResponseEntity<EnderecoResponseDTO> adicionar(@RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        var endereco = enderecoService.adicionar(enderecoRequestDTO);

        return ResponseEntity.ok(endereco);
    }

    @GetMapping
    private ResponseEntity<List<EnderecoResponseDTO>> listarTodos() {
        List<EnderecoResponseDTO> list = enderecoService.listarTodos();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    private ResponseEntity<EnderecoResponseDTO> listarporID(@PathVariable Long id) {
        var endereco = enderecoService.listarporid(id);
        return ResponseEntity.ok(endereco);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> excluir(@PathVariable Long id) {
       enderecoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

