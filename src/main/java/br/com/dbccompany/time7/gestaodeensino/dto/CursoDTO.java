package br.com.dbccompany.time7.gestaodeensino.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CursoDTO extends CursoCreateDTO {

    @Schema(description = "ID do curso")
    private Integer idCurso;
}
