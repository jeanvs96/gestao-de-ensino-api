package br.com.dbccompany.time7.gestaodeensino.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Aluno extends Pessoa{
    private Integer idAluno, matricula, idCurso;

}
