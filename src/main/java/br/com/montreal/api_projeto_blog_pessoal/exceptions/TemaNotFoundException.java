package br.com.montreal.api_projeto_blog_pessoal.exceptions;

public class TemaNotFoundException extends RuntimeException {
    public TemaNotFoundException(String message) {
        super(message);
    }
}
