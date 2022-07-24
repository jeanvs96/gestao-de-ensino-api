package br.com.dbccompany.time7.gestaodeensino.dto.aluno;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoCompletoDTO {
    private String nomeAluno;
    private String telefone;
    private String email;
    private Integer matricula;
    private String curso;
    private String logradouroResidencia;
    private Integer numeroResidencia;
    private String cepResidencia;
    private String cidadeResidencia;
    private String estadoResidencia;

}
