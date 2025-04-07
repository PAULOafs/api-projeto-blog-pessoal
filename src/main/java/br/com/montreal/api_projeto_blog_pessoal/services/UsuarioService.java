package br.com.montreal.api_projeto_blog_pessoal.services;

import br.com.montreal.api_projeto_blog_pessoal.config.JwtService;
import br.com.montreal.api_projeto_blog_pessoal.models.Usuario;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.UsuarioLoginDTO;
import br.com.montreal.api_projeto_blog_pessoal.repositories.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public ResponseEntity<Usuario> criarNovoUsuario(Usuario usuario) {
        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    public ResponseEntity<Usuario> atualizarUsuarioPorId(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setUsuario(usuarioAtualizado.getUsuario());
            usuario.setFoto(usuarioAtualizado.getFoto());
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isBlank()) {
                usuario.setSenha(encoder.encode(usuarioAtualizado.getSenha()));
            }
            return ResponseEntity.ok(usuarioRepository.save(usuario));
        }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Void> deletarUsuarioPorId(Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<UsuarioLoginDTO> autenticarUsuarioPorEmailESenha(UsuarioLoginDTO loginDTO) {
        Optional<Usuario> usuario = usuarioRepository.findByUsuario(loginDTO.getUsuario())
                .filter(user -> encoder.matches(loginDTO.getSenha(), user.getSenha()));

        if (usuario.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        Usuario user = usuario.get();
        loginDTO.setId(user.getId());
        loginDTO.setNome(user.getNome());
        loginDTO.setFoto(user.getFoto());
        loginDTO.setToken(jwtService.generateToken(user));
        loginDTO.setSenha(null);

        return ResponseEntity.ok(loginDTO);
    }
}
