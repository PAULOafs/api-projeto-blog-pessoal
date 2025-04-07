package br.com.montreal.api_projeto_blog_pessoal.models.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginDTO{
    private Long id;

    private String nome;

    private String usuario;

    private String senha;

    private String foto;

    private String token;
}
