package br.com.dbccompany.time7.gestaodeensino.dto;

import br.com.dbccompany.time7.gestaodeensino.entity.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfessorCreateDTO {

    @Schema(description = "Nome do professor")
    @NotBlank
    private String nome;

    @Schema(description = "Telefone do professor")
    @NotBlank
    private String telefone;

    @Schema(description = "E-mail do professor")
    @NotBlank
    private String email;

    @Schema(description = "Registro de trabalho do professor")
    @NotEmpty
    private Integer registroTrabalho;

    @Schema(description = "ID do endereço do professor no banco de dados")
    @NotEmpty
    private Integer idEndereco;

    @Schema(description = "Salário do professor")
    @NotEmpty
    private Double salario;

    @Schema(description = "Cargo do colaborador")
    private String cargo;
}
