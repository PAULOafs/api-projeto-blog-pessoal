package br.com.montreal.api_projeto_blog_pessoal.controllers;

import br.com.montreal.api_projeto_blog_pessoal.models.Usuario;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.UsuarioLoginDTO;
import br.com.montreal.api_projeto_blog_pessoal.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarNovoUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setUsuario("joao@email.com");
        usuario.setSenha("123");

        when(usuarioService.criarNovoUsuario(usuario)).thenReturn(ResponseEntity.ok(usuario));

        ResponseEntity<Usuario> response = usuarioController.criarNovoUsuario(usuario);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
        verify(usuarioService).criarNovoUsuario(usuario);
    }

    @Test
    void deveAtualizarUsuarioPorId() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");

        when(usuarioService.atualizarUsuarioPorId(id, usuario)).thenReturn(ResponseEntity.ok(usuario));

        ResponseEntity<Usuario> response = usuarioController.atualizarUsuarioPorId(id, usuario);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Maria", response.getBody().getNome());
        verify(usuarioService).atualizarUsuarioPorId(id, usuario);
    }

    @Test
    void deveDeletarUsuarioPorId() {
        Long id = 1L;

        when(usuarioService.deletarUsuarioPorId(id)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = usuarioController.deletarUsuarioPorId(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(usuarioService).deletarUsuarioPorId(id);
    }

    @Test
    void deveAutenticarUsuario() {
        UsuarioLoginDTO loginDTO = new UsuarioLoginDTO();
        loginDTO.setUsuario("teste@email.com");
        loginDTO.setSenha("123");

        UsuarioLoginDTO responseDTO = new UsuarioLoginDTO();
        responseDTO.setUsuario("teste@email.com");
        responseDTO.setToken("jwt-token");

        when(usuarioService.autenticarUsuarioPorEmailESenha(loginDTO)).thenReturn(ResponseEntity.ok(responseDTO));

        ResponseEntity<UsuarioLoginDTO> response = usuarioController.authenticarUsuarioPorEmailESenha(loginDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("jwt-token", response.getBody().getToken());
        verify(usuarioService).autenticarUsuarioPorEmailESenha(loginDTO);
    }
}
