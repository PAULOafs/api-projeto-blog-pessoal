package br.com.montreal.api_projeto_blog_pessoal.controllers;

import br.com.montreal.api_projeto_blog_pessoal.controllers.docs.TemControllerDoc;
import br.com.montreal.api_projeto_blog_pessoal.models.Tema;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.MensagemDTO;
import br.com.montreal.api_projeto_blog_pessoal.services.TemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TemaController implements TemControllerDoc {

    private final TemaService temaService;

    public TemaController(TemaService temaService) {
        this.temaService = temaService;
    }

    public ResponseEntity<Tema> criarNovoTema(Tema tema) {
        return ResponseEntity.ok(temaService.criarNovoTema(tema));
    }

    public ResponseEntity<Tema> atualizarTemaPorId(Long id, Tema tema) {
        return ResponseEntity.ok(temaService.atualizarTemaPorId(id, tema));
    }

    public ResponseEntity<MensagemDTO> deletarTemaPorId(Long id) {
        return ResponseEntity.ok(temaService.deletarTemaPorId(id));
    }

    public ResponseEntity<List<Tema>> listarTemas() {
        return ResponseEntity.ok(temaService.listarTodosOsTemas());
    }
}
