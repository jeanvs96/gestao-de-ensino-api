package br.com.dbccompany.time7.gestaodeensino.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfessorDTO extends ProfessorCreateDTO {

    @Schema(description = "ID do professor")
    private Integer idProfessor;
}
