package br.com.dbccompany.time7.gestaodeensino.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotaCreateDTO {

    @Schema(description = "Nota 1 do Aluno")
    private Double nota1;

    @Schema(description = "Nota 1 do Aluno")
    private Double nota2;

    @Schema(description = "Nota 1 do Aluno")
    private Double nota3;

    @Schema(description = "Nota 1 do Aluno")
    private Double nota4;
}
