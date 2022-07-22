package br.com.dbccompany.time7.gestaodeensino.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioAlunosMaioresNotasDTO {
    private String nomeAluno;
    private String nomeCurso;
    private Integer matricula;
    private Double media;
}
