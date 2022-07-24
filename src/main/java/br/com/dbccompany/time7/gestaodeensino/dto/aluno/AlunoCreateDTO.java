package br.com.dbccompany.time7.gestaodeensino.dto.aluno;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AlunoCreateDTO {
    @Schema(description = "Nome do aluno")
    @NotBlank
    private String nome;

    @Schema(description = "Telefone do aluno")
    @NotBlank
    private String telefone;

    @Schema(description = "E-mail do aluno")
    @NotBlank
    private String email;

    @Schema(description = "Identificador único do curso do aluno")
    @NotNull
    private Integer idCurso;

    @Schema(description = "Identificador único do endereço do aluno")
    @NotNull
    private Integer idEndereco;
}
