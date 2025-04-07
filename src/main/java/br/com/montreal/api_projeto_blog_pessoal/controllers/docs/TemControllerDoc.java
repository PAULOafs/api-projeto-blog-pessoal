package br.com.montreal.api_projeto_blog_pessoal.controllers.docs;

import br.com.montreal.api_projeto_blog_pessoal.models.Tema;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.MensagemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Tema", description = "Gerenciamento dos temas das postagens")
public interface TemControllerDoc {

    @Operation(summary = "Cadastrar novo tema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tema criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tema.class))),
            @ApiResponse(responseCode = "400", description = "Tema já existente ou dados inválidos", content = @Content)
    })
    @PostMapping
    ResponseEntity<Tema> criarNovoTema(@RequestBody Tema tema);

    @Operation(summary = "Atualizar tema por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tema atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tema.class))),
            @ApiResponse(responseCode = "404", description = "Tema não encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    ResponseEntity<Tema> atualizarTemaPorId(
            @Parameter(description = "ID do tema") @PathVariable Long id,
            @RequestBody Tema tema
    );

    @Operation(summary = "Deletar tema por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tema deletado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tema não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    ResponseEntity<MensagemDTO> deletarTemaPorId(@PathVariable Long id);

    @Operation(summary = "Listar todos os temas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de temas retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tema.class)))
    })
    @GetMapping
    ResponseEntity<List<Tema>> listarTemas();
}
