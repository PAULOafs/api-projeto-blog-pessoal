package br.com.montreal.api_projeto_blog_pessoal.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data @Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "tb_postagens")
public class Postagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @Column(nullable = false)
    private LocalDateTime data = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "tema_id")
    private Tema tema;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
