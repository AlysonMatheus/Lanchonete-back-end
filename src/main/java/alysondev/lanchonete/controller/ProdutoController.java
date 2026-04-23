package alysondev.lanchonete.controller;


import alysondev.lanchonete.dtos.response.ProdutoResponseDTO;
import alysondev.lanchonete.dtos.request.ProdutoRequestDTO;
import alysondev.lanchonete.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "http://localhost:4200")

public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/adicionar")
    private ResponseEntity<ProdutoResponseDTO> adicionar(@RequestBody ProdutoRequestDTO produtoRequestDTO) {
        var produto = produtoService.criar(produtoRequestDTO);
        return ResponseEntity.ok(produto);
    }

    @PatchMapping("/desativar/{id}")
    public void desativar(@PathVariable Long id) {
        produtoService.desativarProduto(id);

    }

    @PatchMapping("/ativar/{id}")
    public void ativar(@PathVariable Long id) {
        produtoService.ativarProduto(id);

    }
    @GetMapping("/cardapio")
    public ResponseEntity<List<ProdutoResponseDTO>>cardapio(){
        List<ProdutoResponseDTO> cardapio = produtoService.listarAtivos();
        return ResponseEntity.ok(cardapio);
    }


    @GetMapping()
    private ResponseEntity<List<ProdutoResponseDTO>> listar() {
        var produto = produtoService.listar();
        return ResponseEntity.ok(produto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id){
        ProdutoResponseDTO dto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("editar/{id}")
    private ResponseEntity<ProdutoResponseDTO> editar(@Valid @PathVariable  Long id, @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        var produto = produtoService.editar(id, produtoRequestDTO);
        return ResponseEntity.ok(produto);
    }

}
