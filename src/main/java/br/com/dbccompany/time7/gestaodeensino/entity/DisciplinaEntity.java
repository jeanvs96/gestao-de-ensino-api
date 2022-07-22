package br.com.dbccompany.time7.gestaodeensino.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisciplinaEntity {

    private Integer idDisciplina;
    private String nome;
    private Integer idProfessor;
}
