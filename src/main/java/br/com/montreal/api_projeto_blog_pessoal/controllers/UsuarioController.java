package br.com.montreal.api_projeto_blog_pessoal.controllers;

import br.com.montreal.api_projeto_blog_pessoal.config.JwtService;
import br.com.montreal.api_projeto_blog_pessoal.controllers.docs.UsuarioControllerDoc;
import br.com.montreal.api_projeto_blog_pessoal.models.Usuario;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.UsuarioLoginDTO;
import br.com.montreal.api_projeto_blog_pessoal.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController implements UsuarioControllerDoc {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public ResponseEntity<Usuario> criarNovoUsuario( Usuario usuario) {
        return usuarioService.criarNovoUsuario(usuario);
    }

    public ResponseEntity<Usuario> atualizarUsuarioPorId(Long id, Usuario usuario) {
        return usuarioService.atualizarUsuarioPorId(id, usuario);
    }

    public ResponseEntity<Void> deletarUsuarioPorId(Long id) {
        return usuarioService.deletarUsuarioPorId(id);
    }

    public ResponseEntity<UsuarioLoginDTO> authenticarUsuarioPorEmailESenha(UsuarioLoginDTO loginDTO) {
        return usuarioService.autenticarUsuarioPorEmailESenha(loginDTO);
    }
}
