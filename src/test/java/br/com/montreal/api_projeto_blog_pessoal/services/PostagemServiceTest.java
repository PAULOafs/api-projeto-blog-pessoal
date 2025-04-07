package br.com.montreal.api_projeto_blog_pessoal.services;

import br.com.montreal.api_projeto_blog_pessoal.exceptions.AlReadyExistsException;
import br.com.montreal.api_projeto_blog_pessoal.exceptions.TemaNotFoundException;
import br.com.montreal.api_projeto_blog_pessoal.models.Postagem;
import br.com.montreal.api_projeto_blog_pessoal.models.Tema;
import br.com.montreal.api_projeto_blog_pessoal.models.Usuario;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.MensagemDTO;
import br.com.montreal.api_projeto_blog_pessoal.repositories.PostagemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostagemServiceTest {
    @Mock
    private PostagemRepository postagemRepository;

    @InjectMocks
    private PostagemService postagemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Postagem criarPostagemFake() {
        Tema tema = new Tema(1L, "Tecnologia");
        Usuario usuario = new Usuario(1L, "João", "joao@email.com", "123456", null, null);
        return new Postagem(null, "Título", "Texto", LocalDateTime.now(), tema, usuario);
    }

    @Test
    void deveCriarNovaPostagem() {
        Postagem postagem = criarPostagemFake();

        when(postagemRepository.existsByTitulo("Título")).thenReturn(false);
        when(postagemRepository.save(postagem)).thenReturn(postagem);

        Postagem resultado = postagemService.criarNovaPostagem(postagem);

        assertEquals(postagem, resultado);
        verify(postagemRepository).save(postagem);
    }

    @Test
    void deveLancarExcecaoQuandoTituloExistente() {
        Postagem postagem = criarPostagemFake();
        when(postagemRepository.existsByTitulo("Título")).thenReturn(true);

        assertThrows(AlReadyExistsException.class, () -> postagemService.criarNovaPostagem(postagem));
        verify(postagemRepository, never()).save(any());
    }

    @Test
    void deveAtualizarPostagemPorId() {
        Long id = 1L;
        Tema tema = new Tema(1L, "Tech");
        Usuario usuario = new Usuario(1L, "João", "joao@email.com", "123", null, null);
        Postagem postagemExistente = new Postagem(id, "Antigo", "Texto", LocalDateTime.now(), tema, usuario);
        Postagem novaPostagem = new Postagem(null, "Novo", "Texto novo", LocalDateTime.now(), tema, usuario);

        when(postagemRepository.findById(id)).thenReturn(Optional.of(postagemExistente));
        when(postagemRepository.save(postagemExistente)).thenReturn(postagemExistente);

        Postagem resultado = postagemService.atualizarPostagemPostagemPorId(id, novaPostagem);

        assertEquals("Novo", resultado.getTitulo());
        assertEquals("Texto novo", resultado.getTexto());
        verify(postagemRepository).save(postagemExistente);
    }

    @Test
    void deveLancarExcecaoQuandoAtualizarPostagemInexistente() {
        Long id = 1L;
        when(postagemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TemaNotFoundException.class, () -> postagemService.atualizarPostagemPostagemPorId(id, criarPostagemFake()));
    }

    @Test
    void deveDeletarPostagemPorId() {
        Long id = 1L;
        when(postagemRepository.existsById(id)).thenReturn(true);
        doNothing().when(postagemRepository).deleteById(id);

        MensagemDTO resposta = postagemService.deletarPostagemPorId(id);

        assertEquals("POSTAGEM DELETADA", resposta.getMensagem());
        verify(postagemRepository).deleteById(id);
    }

    @Test
    void deveLancarExcecaoQuandoDeletarPostagemInexistente() {
        Long id = 1L;
        when(postagemRepository.existsById(id)).thenReturn(false);

        assertThrows(TemaNotFoundException.class, () -> postagemService.deletarPostagemPorId(id));
    }

    @Test
    void deveListarTodasPostagens() {
        List<Postagem> postagens = List.of(
                criarPostagemFake(),
                new Postagem(2L, "Outro Título", "Outro texto", LocalDateTime.now(), new Tema(), new Usuario())
        );

        when(postagemRepository.findAll()).thenReturn(postagens);

        List<Postagem> resultado = postagemService.listarTodasPostagens();

        assertEquals(2, resultado.size());
    }

    @Test
    void deveFiltrarPorTemaEUsuario() {
        Long idTema = 1L;
        Long idUsuario = 2L;

        when(postagemRepository.findByTemaIdAndUsuarioId(idTema, idUsuario))
                .thenReturn(List.of(criarPostagemFake()));

        List<Postagem> resultado = postagemService.filtrarPostagensPorTemaOuUsuario(idTema, idUsuario);

        assertEquals(1, resultado.size());
        verify(postagemRepository).findByTemaIdAndUsuarioId(idTema, idUsuario);
    }

    @Test
    void deveFiltrarPorTema() {
        Long idTema = 1L;

        when(postagemRepository.findByTemaId(idTema)).thenReturn(List.of(criarPostagemFake()));

        List<Postagem> resultado = postagemService.filtrarPostagensPorTemaOuUsuario(idTema, null);

        assertEquals(1, resultado.size());
        verify(postagemRepository).findByTemaId(idTema);
    }

    @Test
    void deveFiltrarPorUsuario() {
        Long idUsuario = 2L;

        when(postagemRepository.findByUsuarioId(idUsuario)).thenReturn(List.of(criarPostagemFake()));

        List<Postagem> resultado = postagemService.filtrarPostagensPorTemaOuUsuario(null, idUsuario);

        assertEquals(1, resultado.size());
        verify(postagemRepository).findByUsuarioId(idUsuario);
    }

    @Test
    void deveListarTodasPostagensQuandoFiltrosNulos() {
        when(postagemRepository.findAll()).thenReturn(List.of(criarPostagemFake()));

        List<Postagem> resultado = postagemService.filtrarPostagensPorTemaOuUsuario(null, null);

        assertEquals(1, resultado.size());
        verify(postagemRepository).findAll();
    }
}
