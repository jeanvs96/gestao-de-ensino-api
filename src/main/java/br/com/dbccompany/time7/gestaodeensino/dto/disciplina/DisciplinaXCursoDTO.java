package br.com.dbccompany.time7.gestaodeensino.dto.disciplina;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisciplinaXCursoDTO extends DisciplinaXCursoCreateDTO {

    @Schema(description = "ID do curso da disciplina")
    private Integer idCurso;
}
