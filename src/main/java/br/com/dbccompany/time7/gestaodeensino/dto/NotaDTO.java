package br.com.dbccompany.time7.gestaodeensino.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotaDTO {

    @Schema(description = "ID único que relaciona uma disciplina e um aluno")
    private Integer idNota;

    @Schema(description = "ID da disciplina relacionada à nota")
    private Integer idDisciplina;

    @Schema(description = "ID do aluno relacionado à nota")
    private Integer idAluno;

    @Schema(description = "Nota 1 do aluno, referente à disciplina")
    private Double nota1;

    @Schema(description = "Nota 1 do aluno, referente à disciplina")
    private Double nota2;

    @Schema(description = "Nota 1 do aluno, referente à disciplina")
    private Double nota3;

    @Schema(description = "Nota 1 do aluno, referente à disciplina")
    private Double nota4;

    @Schema(description = "Nota 1 do aluno, referente à disciplina")
    private Double media;
}
