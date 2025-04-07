package br.com.montreal.api_projeto_blog_pessoal.repositories;

import br.com.montreal.api_projeto_blog_pessoal.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsuario(String usuario);
}
