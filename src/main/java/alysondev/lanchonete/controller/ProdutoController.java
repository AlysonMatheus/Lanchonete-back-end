package alysondev.lanchonete.controller;


import alysondev.lanchonete.dtos.response.ProdutoResponseDTO;
import alysondev.lanchonete.dtos.request.ProdutoRequestDTO;
import alysondev.lanchonete.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/adicionar")
    private ResponseEntity<ProdutoResponseDTO> adicionar(@RequestBody ProdutoRequestDTO produtoRequestDTO) {
        var produto = produtoService.criar(produtoRequestDTO);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/desativar/{id}")
    public void desativar(@PathVariable Long id) {
        produtoService.desativarProduto(id);

    }

    @PostMapping("/ativar/{id}")
    public void ativar(@PathVariable Long id) {
        produtoService.ativarProduto(id);

    }


    @GetMapping("/listar")
    private ResponseEntity<List<ProdutoResponseDTO>> listar() {
        var produto = produtoService.listar();
        return ResponseEntity.ok(produto);
    }

    @PutMapping("editar/{id}")
    private ResponseEntity<ProdutoResponseDTO> editar(@RequestBody @RequestParam Long id, ProdutoRequestDTO produtoRequestDTO) {
        var produto = produtoService.editar(id, produtoRequestDTO);
        return ResponseEntity.ok(produto);
    }

}
