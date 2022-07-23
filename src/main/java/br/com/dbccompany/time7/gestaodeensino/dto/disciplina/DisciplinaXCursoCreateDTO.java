package br.com.dbccompany.time7.gestaodeensino.dto.disciplina;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisciplinaXCursoCreateDTO {

    @Schema(description = "ID da disciplina")
    @NotNull
    private Integer idDisciplina;
}
