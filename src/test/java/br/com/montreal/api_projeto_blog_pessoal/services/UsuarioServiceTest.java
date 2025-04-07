package br.com.montreal.api_projeto_blog_pessoal.services;

import br.com.montreal.api_projeto_blog_pessoal.config.JwtService;
import br.com.montreal.api_projeto_blog_pessoal.models.Usuario;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.UsuarioLoginDTO;
import br.com.montreal.api_projeto_blog_pessoal.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {
    private UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        usuarioService = new UsuarioService(usuarioRepository, passwordEncoder, jwtService);
    }

    @Test
    void deveCriarNovoUsuario() {
        Usuario novoUsuario = new Usuario(1L, "João", "joao@email.com", "123", null, null);

        when(usuarioRepository.findByUsuario("joao@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123")).thenReturn("hashed123");
        when(usuarioRepository.save(ArgumentMatchers.any(Usuario.class))).thenReturn(novoUsuario);

        ResponseEntity<Usuario> resposta = usuarioService.criarNovoUsuario(novoUsuario);

        assertEquals(200, resposta.getStatusCodeValue());
        verify(usuarioRepository).save(novoUsuario);
    }

    @Test
    void naoDeveCriarUsuarioExistente() {
        Usuario existente = new Usuario();
        existente.setUsuario("existente@email.com");

        when(usuarioRepository.findByUsuario("existente@email.com")).thenReturn(Optional.of(existente));

        ResponseEntity<Usuario> resposta = usuarioService.criarNovoUsuario(existente);

        assertEquals(400, resposta.getStatusCodeValue());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveAtualizarUsuarioExistente() {
        Long id = 1L;
        Usuario usuario = new Usuario(id, "João", "joao@email.com", "senha", null, null);
        Usuario atualizado = new Usuario(null, "João Atualizado", "joao@email.com", "novaSenha", "foto", null);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("novaSenha")).thenReturn("hashedNovaSenha");
        when(usuarioRepository.save(any())).thenReturn(usuario);

        ResponseEntity<Usuario> resposta = usuarioService.atualizarUsuarioPorId(id, atualizado);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals("João Atualizado", resposta.getBody().getNome());
        verify(usuarioRepository).save(any());
    }

    @Test
    void deveRetornarNotFoundAoAtualizarUsuarioInexistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> resposta = usuarioService.atualizarUsuarioPorId(1L, new Usuario());

        assertEquals(404, resposta.getStatusCodeValue());
    }

    @Test
    void deveDeletarUsuarioExistente() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> resposta = usuarioService.deletarUsuarioPorId(1L);

        assertEquals(204, resposta.getStatusCodeValue());
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void deveRetornarNotFoundAoDeletarUsuarioInexistente() {
        when(usuarioRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> resposta = usuarioService.deletarUsuarioPorId(1L);

        assertEquals(404, resposta.getStatusCodeValue());
        verify(usuarioRepository, never()).deleteById(1L);
    }

    @Test
    void deveAutenticarUsuarioComSucesso() {
        Usuario usuario = new Usuario(1L, "Maria", "maria@email.com", "senhaHash", "foto.jpg", null);
        UsuarioLoginDTO login = new UsuarioLoginDTO(null, null, "maria@email.com", "senha", null, null);

        when(usuarioRepository.findByUsuario("maria@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha", "senhaHash")).thenReturn(true);
        when(jwtService.generateToken(usuario)).thenReturn("jwt_token");

        ResponseEntity<UsuarioLoginDTO> resposta = usuarioService.autenticarUsuarioPorEmailESenha(login);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals("jwt_token", resposta.getBody().getToken());
    }

    @Test
    void naoDeveAutenticarUsuarioComSenhaInvalida() {
        Usuario usuario = new Usuario(1L, "Maria", "maria@email.com", "senhaHash", "foto.jpg", null);
        UsuarioLoginDTO login = new UsuarioLoginDTO(null, null, "maria@email.com", "senhaErrada", null, null);

        when(usuarioRepository.findByUsuario("maria@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senhaErrada", "senhaHash")).thenReturn(false);

        ResponseEntity<UsuarioLoginDTO> resposta = usuarioService.autenticarUsuarioPorEmailESenha(login);

        assertEquals(401, resposta.getStatusCodeValue());
    }
}
