package br.com.montreal.api_projeto_blog_pessoal.repositories;

import br.com.montreal.api_projeto_blog_pessoal.models.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {
    boolean existsByDescricao(String descricao);
}
