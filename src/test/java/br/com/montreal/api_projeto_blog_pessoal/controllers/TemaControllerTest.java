package br.com.montreal.api_projeto_blog_pessoal.controllers;

import br.com.montreal.api_projeto_blog_pessoal.models.Tema;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.MensagemDTO;
import br.com.montreal.api_projeto_blog_pessoal.services.TemaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TemaControllerTest {

    @Mock
    private TemaService temaService;

    @InjectMocks
    private TemaController temaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarNovoTema() {
        Tema tema = new Tema(null, "Java");
        Tema temaSalvo = new Tema(1L, "Java");

        when(temaService.criarNovoTema(tema)).thenReturn(temaSalvo);

        ResponseEntity<Tema> response = temaController.criarNovoTema(tema);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(temaSalvo, response.getBody());
        verify(temaService).criarNovoTema(tema);
    }

    @Test
    void deveAtualizarTemaPorId() {
        Long id = 1L;
        Tema tema = new Tema(null, "Spring");
        Tema temaAtualizado = new Tema(id, "Spring");

        when(temaService.atualizarTemaPorId(id, tema)).thenReturn(temaAtualizado);

        ResponseEntity<Tema> response = temaController.atualizarTemaPorId(id, tema);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(temaAtualizado, response.getBody());
        verify(temaService).atualizarTemaPorId(id, tema);
    }

    @Test
    void deveDeletarTemaPorId() {
        Long id = 1L;
        MensagemDTO mensagem = MensagemDTO.builder().mensagem("TEMA DELETADO").build();

        when(temaService.deletarTemaPorId(id)).thenReturn(mensagem);

        ResponseEntity<MensagemDTO> response = temaController.deletarTemaPorId(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("TEMA DELETADO", response.getBody().getMensagem());
        verify(temaService).deletarTemaPorId(id);
    }

    @Test
    void deveListarTodosOsTemas() {
        List<Tema> temas = Arrays.asList(
                new Tema(1L, "Java"),
                new Tema(2L, "Spring Boot")
        );

        when(temaService.listarTodosOsTemas()).thenReturn(temas);

        ResponseEntity<List<Tema>> response = temaController.listarTemas();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Java", response.getBody().get(0).getDescricao());
        verify(temaService).listarTodosOsTemas();
    }
}
