package br.com.dbccompany.time7.gestaodeensino.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfessorDTO extends ProfessorCreateDTO {

    @Schema(description = "ID do professor")
    private Integer idProfessor;

    @Schema(description = "Registro de trabalho do professor")
    private Integer registroTrabalho;
}
