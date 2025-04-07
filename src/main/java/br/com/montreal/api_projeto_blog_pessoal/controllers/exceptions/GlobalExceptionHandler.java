package br.com.montreal.api_projeto_blog_pessoal.controllers.exceptions;

import br.com.montreal.api_projeto_blog_pessoal.exceptions.*;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.MensagemDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<MensagemDTO> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        return criarResposta(HttpStatus.NOT_FOUND, "RECURSO NAO ENCONTRADO");
    }

    @ExceptionHandler(UsuarioJaExisteException.class)
    public ResponseEntity<MensagemDTO> handleUsuarioJaExiste(UsuarioJaExisteException ex) {
        return criarResposta(HttpStatus.CONFLICT, "USUARIO EXISTE");
    }

    @ExceptionHandler(RequisicaoInvalidaException.class)
    public ResponseEntity<MensagemDTO> handleRequisicaoInvalida(RequisicaoInvalidaException ex) {
        return criarResposta(HttpStatus.BAD_REQUEST, "REQUISICAO INVALIDA");
    }

    @ExceptionHandler(ErroAutenticacaoException.class)
    public ResponseEntity<MensagemDTO> handleErroAutenticacao(ErroAutenticacaoException ex) {
        return criarResposta(HttpStatus.UNAUTHORIZED, "ERRO AUTH");
    }

    @ExceptionHandler(TemaNotFoundException.class)
    public ResponseEntity<MensagemDTO> handleTemaNotFound(TemaNotFoundException ex) {
        return criarResposta(HttpStatus.NOT_FOUND, "TEMA NAO ENCONTRADO");
    }

    @ExceptionHandler(AlReadyExistsException.class)
    public ResponseEntity<MensagemDTO> handleAlReadyExists(AlReadyExistsException ex) {
        return criarResposta(HttpStatus.CONFLICT, "OBJETO JA EXISTE");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemDTO> handleValidacao(MethodArgumentNotValidException ex) {
        var err = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage()).findFirst().orElse("ERRO VALIDACAO");
        return criarResposta(HttpStatus.BAD_REQUEST, err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MensagemDTO> handleConstraint(ConstraintViolationException ex) {
        return criarResposta(HttpStatus.BAD_REQUEST, "ERRO DE PK");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemDTO> handleOutros(Exception ex) {
        return criarResposta(HttpStatus.INTERNAL_SERVER_ERROR, "ERRO INTERNO DO SERVIDOR");
    }

    private ResponseEntity<MensagemDTO> criarResposta(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status).body(
                new MensagemDTO(mensagem, status.getReasonPhrase(), LocalDateTime.now(), status.value())
        );
    }
}
