package br.com.montreal.api_projeto_blog_pessoal.repositories;

import br.com.montreal.api_projeto_blog_pessoal.models.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {
    List<Postagem> findByTemaIdAndUsuarioId(Long idTema, Long idUsuario);

    List<Postagem> findByTemaId(Long idTema);

    List<Postagem> findByUsuarioId(Long idUsuario);

    boolean existsByTitulo(String titulo);
}
