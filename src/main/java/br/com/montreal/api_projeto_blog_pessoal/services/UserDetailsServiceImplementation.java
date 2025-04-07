package br.com.montreal.api_projeto_blog_pessoal.services;

import br.com.montreal.api_projeto_blog_pessoal.models.Usuario;
import br.com.montreal.api_projeto_blog_pessoal.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//@Slf4j
public class UserDetailsServiceImplementation implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImplementation(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("USUARIO NAO ECONTRADO"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getUsuario()).password(usuario.getSenha()).roles("USER").build();
    }
}
