package br.com.montreal.api_projeto_blog_pessoal.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MensagemDTO {
    private String mensagem;

    private String detalhes;

    private LocalDateTime dataHora = LocalDateTime.now();

    private Integer code;
}
