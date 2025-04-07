package br.com.montreal.api_projeto_blog_pessoal.services;

import br.com.montreal.api_projeto_blog_pessoal.exceptions.AlReadyExistsException;
import br.com.montreal.api_projeto_blog_pessoal.exceptions.TemaNotFoundException;
import br.com.montreal.api_projeto_blog_pessoal.models.Tema;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.MensagemDTO;
import br.com.montreal.api_projeto_blog_pessoal.repositories.TemaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TemaServiceTest {

    @Mock
    private TemaRepository temaRepository;

    @InjectMocks
    private TemaService temaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarNovoTema() {
        Tema tema = new Tema(null, "Programação");

        when(temaRepository.existsByDescricao("Programação")).thenReturn(false);
        when(temaRepository.save(tema)).thenReturn(tema);

        Tema resultado = temaService.criarNovoTema(tema);

        assertNotNull(resultado);
        assertEquals("Programação", resultado.getDescricao());
        verify(temaRepository).save(tema);
    }

    @Test
    void naoDeveCriarTemaExistente() {
        Tema tema = new Tema(null, "DevOps");

        when(temaRepository.existsByDescricao("DevOps")).thenReturn(true);

        assertThrows(AlReadyExistsException.class, () -> temaService.criarNovoTema(tema));
        verify(temaRepository, never()).save(any());
    }

    @Test
    void deveAtualizarTemaExistente() {
        Long id = 1L;
        Tema temaAtual = new Tema(id, "Java");
        Tema novoTema = new Tema(null, "Spring Boot");

        when(temaRepository.findById(id)).thenReturn(Optional.of(temaAtual));
        when(temaRepository.save(any(Tema.class))).thenReturn(temaAtual);

        Tema resultado = temaService.atualizarTemaPorId(id, novoTema);

        assertEquals("Spring Boot", resultado.getDescricao());
        verify(temaRepository).save(temaAtual);
    }

    @Test
    void naoDeveAtualizarTemaInexistente() {
        when(temaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TemaNotFoundException.class, () -> temaService.atualizarTemaPorId(99L, new Tema()));
    }

    @Test
    void deveDeletarTema() {
        Long id = 1L;

        when(temaRepository.existsById(id)).thenReturn(true);
        doNothing().when(temaRepository).deleteById(id);

        MensagemDTO resposta = temaService.deletarTemaPorId(id);

        assertEquals("TEMA DELETADO", resposta.getMensagem());
        verify(temaRepository).deleteById(id);
    }

    @Test
    void naoDeveDeletarTemaInexistente() {
        when(temaRepository.existsById(99L)).thenReturn(false);

        assertThrows(TemaNotFoundException.class, () -> temaService.deletarTemaPorId(99L));
        verify(temaRepository, never()).deleteById(any());
    }

    @Test
    void deveListarTodosOsTemas() {
        List<Tema> temas = Arrays.asList(
                new Tema(1L, "Java"),
                new Tema(2L, "Spring")
        );

        when(temaRepository.findAll()).thenReturn(temas);

        List<Tema> resultado = temaService.listarTodosOsTemas();

        assertEquals(2, resultado.size());
        verify(temaRepository).findAll();
    }
}
