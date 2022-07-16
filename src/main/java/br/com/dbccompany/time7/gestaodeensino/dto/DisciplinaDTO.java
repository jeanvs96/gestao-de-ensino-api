package br.com.dbccompany.time7.gestaodeensino.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisciplinaDTO {

    @Schema(description = "ID exclusivo da disciplina")
    private Integer idDisciplina;;
}
