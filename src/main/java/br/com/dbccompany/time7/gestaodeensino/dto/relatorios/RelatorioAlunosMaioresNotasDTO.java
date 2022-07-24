package br.com.dbccompany.time7.gestaodeensino.dto.relatorios;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioAlunosMaioresNotasDTO {
    private String nomeCurso;
    private String nomeAluno;
    private Integer matricula;
    private Double media;

}
