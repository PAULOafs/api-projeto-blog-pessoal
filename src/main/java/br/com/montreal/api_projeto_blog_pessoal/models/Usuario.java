package br.com.montreal.api_projeto_blog_pessoal.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data @Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String senha;

    private String foto;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Postagem> postagens;
}
