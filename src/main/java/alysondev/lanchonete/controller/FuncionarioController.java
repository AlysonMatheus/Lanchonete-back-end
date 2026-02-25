package alysondev.lanchonete.controller;


import alysondev.lanchonete.dtos.request.FuncionarioRequestDTO;
import alysondev.lanchonete.dtos.response.FuncionarioResponseDTO;
import alysondev.lanchonete.services.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
   @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<FuncionarioResponseDTO> cadastrar(@RequestBody FuncionarioRequestDTO funcionarioRequestDTO) {
        var funcionario = funcionarioService.cadastrar(funcionarioRequestDTO);
        return ResponseEntity.ok(funcionario);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<FuncionarioResponseDTO> editar( @PathVariable Long id, @RequestBody @Valid FuncionarioRequestDTO funcionarioRequestDTO) {
        var funcionario = funcionarioService.editar(id,funcionarioRequestDTO);
        return ResponseEntity.ok(funcionario);
    }
}