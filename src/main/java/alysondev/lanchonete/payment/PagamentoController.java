package alysondev.lanchonete.payment;


import alysondev.lanchonete.payment.dtos.TransacaoResponseDTO;
import alysondev.lanchonete.services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks/mercadopagoo")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/{pedidoId}")
    public ResponseEntity<TransacaoResponseDTO> criarTransacao(@PathVariable Long id){
        return ResponseEntity.ok(pagamentoService.criarTransacao(id));


    }

}
