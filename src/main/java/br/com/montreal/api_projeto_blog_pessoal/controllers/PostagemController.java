package br.com.montreal.api_projeto_blog_pessoal.controllers;

import br.com.montreal.api_projeto_blog_pessoal.controllers.docs.PostagemControllerDoc;
import br.com.montreal.api_projeto_blog_pessoal.models.Postagem;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.MensagemDTO;
import br.com.montreal.api_projeto_blog_pessoal.services.PostagemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostagemController implements PostagemControllerDoc {
    private final PostagemService postagemService;

    public PostagemController(PostagemService postagemService) {
        this.postagemService = postagemService;
    }

    public ResponseEntity<Postagem> criarNovaPostagem(Postagem postagem) {
        return ResponseEntity.ok(postagemService.criarNovaPostagem(postagem));
    }

    public ResponseEntity<Postagem> atualizarPostagemPostagemPorId(Long id, Postagem postagem) {
        return ResponseEntity.ok(postagemService.atualizarPostagemPostagemPorId(id, postagem));
    }

    public ResponseEntity<MensagemDTO> deletarPostagemPorId(Long id) {
        return ResponseEntity.ok(postagemService.deletarPostagemPorId(id));
    }

    public ResponseEntity<List<Postagem>> listar() {
        return ResponseEntity.ok(postagemService.listarTodasPostagens());
    }

    public ResponseEntity<List<Postagem>> filtrarPostagensPorTemaOuUsuario(Long autor, Long tema) {
        return ResponseEntity.ok(postagemService.filtrarPostagensPorTemaOuUsuario(tema, autor));
    }
}
