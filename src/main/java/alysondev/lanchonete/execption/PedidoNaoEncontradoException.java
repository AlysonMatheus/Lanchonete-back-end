package alysondev.lanchonete.execption;

public class PedidoNaoEncontradoException extends RuntimeException{
    public PedidoNaoEncontradoException(String message) {
        super(message);
    }
}
