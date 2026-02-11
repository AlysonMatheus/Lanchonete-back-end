package alysondev.lanchonete.execption;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            ProdutoNaoEncontradoException.class,
            PedidoNaoEncontradoException.class,
            ClienteNaoEncontradoException.class
    })
    public ResponseEntity<ErroDTO> recursoNaoEncontrado(RuntimeException ex) {
        ErroDTO erroDTO = new ErroDTO(ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDTO);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        String mensagem = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErroDTO erroDTO = new ErroDTO(mensagem, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroDTO);
    }

//    @ExceptionHandler(ProdutoNaoEncontradoException.class)
//    public  ResponseEntity<ErroDTO> produtoNaoEncontrado(ProdutoNaoEncontradoException ex){
//        ErroDTO erroDTO = new ErroDTO(ex.getMessage(), LocalDateTime.now());
//
//        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDTO);
//    }
//    @ExceptionHandler(PedidoNaoEncontradoException.class)
//    public ResponseEntity<ErroDTO> pedidoNaoEncontrado(PedidoNaoEncontradoException ex){
//        ErroDTO erroDTO = new ErroDTO(ex.getMessage(), LocalDateTime.now());
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDTO);
//    }
//    @ExceptionHandler(ClienteNaoEncontradoException.class)
//    public ResponseEntity<ErroDTO> ClienteNaoEncontrado(ClienteNaoEncontradoException ex){
//        ErroDTO erroDTO = new ErroDTO(ex.getMessage(), LocalDateTime.now());
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDTO);
}

