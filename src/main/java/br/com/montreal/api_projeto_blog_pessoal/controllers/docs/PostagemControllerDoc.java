package br.com.montreal.api_projeto_blog_pessoal.controllers.docs;

import br.com.montreal.api_projeto_blog_pessoal.models.Postagem;
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

@RequestMapping("/v1/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Postagem", description = "Gerenciamento das postagens do blog")
public interface PostagemControllerDoc {

    @Operation(summary = "Cadastrar nova postagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Postagem criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Postagem.class))),
            @ApiResponse(responseCode = "400", description = "Postagem já existente ou dados inválidos", content = @Content)
    })
    @PostMapping
    ResponseEntity<Postagem> criarNovaPostagem(@RequestBody Postagem postagem);

    @Operation(summary = "Atualizar postagem por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Postagem atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Postagem.class))),
            @ApiResponse(responseCode = "404", description = "Postagem não encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    ResponseEntity<Postagem> atualizarPostagemPostagemPorId(
            @Parameter(description = "ID da postagem") @PathVariable Long id,
            @RequestBody Postagem postagem
    );

    @Operation(summary = "Deletar postagem por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Postagem deletada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MensagemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Postagem não encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    ResponseEntity<MensagemDTO> deletarPostagemPorId(@PathVariable Long id);

    @Operation(summary = "Listar todas as postagens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de postagens retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Postagem.class)))
    })
    @GetMapping
    ResponseEntity<List<Postagem>> listar();

    @Operation(summary = "Filtrar postagens por tema ou autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Postagens filtradas com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Postagem.class)))
    })
    @GetMapping("/filtro")
    ResponseEntity<List<Postagem>> filtrarPostagensPorTemaOuUsuario(
            @Parameter(description = "ID do autor") @RequestParam(required = false) Long autor,
            @Parameter(description = "ID do tema") @RequestParam(required = false) Long tema
    );
}
