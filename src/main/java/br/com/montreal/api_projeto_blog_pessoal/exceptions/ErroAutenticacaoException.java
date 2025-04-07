package br.com.montreal.api_projeto_blog_pessoal.exceptions;

public class ErroAutenticacaoException extends RuntimeException {
    public ErroAutenticacaoException(String message) {
        super(message);
    }
}
