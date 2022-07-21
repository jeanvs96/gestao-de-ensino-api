package br.com.dbccompany.time7.gestaodeensino.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AlunoUpdateDTO {
    @Schema(description = "Nome do aluno")
    private String nome;

    @Schema(description = "Telefone do aluno")
    private String telefone;

    @Schema(description = "E-mail do aluno")
    private String email;

    @Schema(description = "Identificador único do curso do aluno")
    @NotNull
    private Integer idCurso;
}
