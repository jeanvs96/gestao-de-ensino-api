package br.com.dbccompany.time7.gestaodeensino.dto.relatorios;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioAlunosMaioresNotasDTO {
    private String nomeAluno;
    private Integer matricula;
    private String disciplina;
    private String curso;
    private Double media;
}
