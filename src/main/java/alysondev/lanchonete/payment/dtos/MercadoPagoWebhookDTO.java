package alysondev.lanchonete.payment.dtos;

public record MercadoPagoWebhookDTO(Long id, String action, String type, DataDTO data) {
    public record DataDTO(String id) {}
}
