package br.com.montreal.api_projeto_blog_pessoal.models;

import jakarta.persistence.*;
import lombok.*;

@Data @Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_temas")
public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;
}
