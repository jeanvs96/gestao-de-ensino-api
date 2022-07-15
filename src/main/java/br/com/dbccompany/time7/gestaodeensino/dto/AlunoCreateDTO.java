package br.com.dbccompany.time7.gestaodeensino.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlunoCreateDTO {
    @Schema(description = "Nome do aluno")
    private String nome;

    @Schema(description = "Telefone do aluno")
    private String telefone;

    @Schema(description = "E-mail do aluno")
    private String email;

    @Schema(description = "Número de matrícula do aluno")
    private Integer matricula;

    @Schema(description = "Identificador único do curso do aluno")
    private Integer idCurso;

    @Schema(description = "Identificador único do endereço do aluno")
    private Integer idEndereco;
}
