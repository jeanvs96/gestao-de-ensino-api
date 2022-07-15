package br.com.dbccompany.time7.gestaodeensino.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;

@Data
public class AlunoDTO extends AlunoCreateDTO {
    @Schema(description = "Identificador único do Aluno")
    @NonNull
    private Integer idAluno;
}
