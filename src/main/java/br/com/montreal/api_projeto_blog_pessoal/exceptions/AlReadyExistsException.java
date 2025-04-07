package br.com.montreal.api_projeto_blog_pessoal.exceptions;

public class AlReadyExistsException extends RuntimeException {
    public AlReadyExistsException(String message) {
        super(message);
    }
}
