package br.com.dbccompany.time7.gestaodeensino.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AlunoEntity extends PessoaEntity {
    private Integer idAluno, matricula, idCurso;

}
