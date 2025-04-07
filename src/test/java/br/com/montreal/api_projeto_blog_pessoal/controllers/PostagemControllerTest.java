package br.com.montreal.api_projeto_blog_pessoal.controllers;

import br.com.montreal.api_projeto_blog_pessoal.models.Postagem;
import br.com.montreal.api_projeto_blog_pessoal.models.Tema;
import br.com.montreal.api_projeto_blog_pessoal.models.Usuario;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.MensagemDTO;
import br.com.montreal.api_projeto_blog_pessoal.services.PostagemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "joao", roles = {"USER"})
@SpringBootTest
@AutoConfigureMockMvc
class PostagemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostagemService postagemService;

    @Autowired
    private ObjectMapper objectMapper;

    private Postagem criarPostagemFake() {
        Tema tema = new Tema(1L, "Tecnologia");
        Usuario usuario = new Usuario(1L, "João", "joao@email.com", "123456", null, null);
        return new Postagem(1L, "Título", "Texto", LocalDateTime.now(), tema, usuario);
    }

    @Test
    void deveCriarNovaPostagem() throws Exception {
        Postagem postagem = criarPostagemFake();

        when(postagemService.criarNovaPostagem(any())).thenReturn(postagem);

        mockMvc.perform(post("/v1/postagens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postagem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Título"));
    }

    @Test
    void deveAtualizarPostagemPorId() throws Exception {
        Long id = 1L;
        Postagem postagemAtualizada = new Postagem(id, "Atualizado", "Texto atualizado", LocalDateTime.now(), new Tema(), new Usuario());

        when(postagemService.atualizarPostagemPostagemPorId(eq(id), any())).thenReturn(postagemAtualizada);

        mockMvc.perform(put("/v1/postagens/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postagemAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Atualizado"));
    }

    @Test
    void deveDeletarPostagemPorId() throws Exception {
        Long id = 1L;
        MensagemDTO resposta = MensagemDTO.builder().mensagem("POSTAGEM DELETADA").build();

        when(postagemService.deletarPostagemPorId(id)).thenReturn(resposta);

        mockMvc.perform(delete("/v1/postagens/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("POSTAGEM DELETADA"));
    }

    @Test
    void deveListarTodasPostagens() throws Exception {
        List<Postagem> lista = List.of(
                criarPostagemFake(),
                new Postagem(2L, "Título 2", "Texto 2", LocalDateTime.now(), new Tema(), new Usuario())
        );

        when(postagemService.listarTodasPostagens()).thenReturn(lista);

        mockMvc.perform(get("/v1/postagens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].titulo").value("Título"))
                .andExpect(jsonPath("$[1].titulo").value("Título 2"));
    }

    @Test
    void deveFiltrarPostagensPorTemaOuUsuario() throws Exception {
        List<Postagem> lista = List.of(
                new Postagem(1L, "Filtrado", "Texto", LocalDateTime.now(), new Tema(), new Usuario())
        );

        when(postagemService.filtrarPostagensPorTemaOuUsuario(10L, 20L)).thenReturn(lista);

        mockMvc.perform(get("/v1/postagens/filtro")
                        .param("tema", "10")
                        .param("autor", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo").value("Filtrado"));
    }
}
