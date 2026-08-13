package alysondev.lanchonete.payment;

import alysondev.lanchonete.payment.dtos.MercadoPagoWebhookDTO;
import alysondev.lanchonete.services.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks/mercadopago")
@RequiredArgsConstructor
public class MercadoPagoWebhookController {
private final PagamentoService pagamentoService;

@PostMapping
  public ResponseEntity<Void> receber(@RequestBody MercadoPagoWebhookDTO mercadoPagoWebhookDTO){
    if ("payment.update".equals(mercadoPagoWebhookDTO.action())|| "paymente.create".equals(mercadoPagoWebhookDTO.action())){
      pagamentoService.processarNotificacao(mercadoPagoWebhookDTO.data().id());
    }
    return ResponseEntity.ok().build();

  }
}
