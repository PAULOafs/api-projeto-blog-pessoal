package br.com.montreal.api_projeto_blog_pessoal.controllers.docs;

import br.com.montreal.api_projeto_blog_pessoal.models.Usuario;
import br.com.montreal.api_projeto_blog_pessoal.models.dtos.UsuarioLoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Usuário", description = "Gerenciamento de usuários e autenticação")
public interface UsuarioControllerDoc {
    @Operation(summary = "Cadastrar novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    ResponseEntity<Usuario> criarNovoUsuario(@RequestBody Usuario usuario);

    @Operation(summary = "Atualizar usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{id}")
    ResponseEntity<Usuario> atualizarUsuarioPorId(
            @Parameter(description = "ID do usuário") @PathVariable Long id,
            @RequestBody Usuario usuario
    );

    @Operation(summary = "Deletar usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarUsuarioPorId(@PathVariable Long id);

    @Operation(summary = "Autenticar usuário (login)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioLoginDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    ResponseEntity<UsuarioLoginDTO> authenticarUsuarioPorEmailESenha(@RequestBody UsuarioLoginDTO loginDTO);
}
