package br.com.dbccompany.time7.gestaodeensino.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoEntity extends Pessoa{

    @Id
    @SequenceGenerator(name = "seq_aluno2", sequenceName = "seq_aluno", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_aluno2")
    @Column(name = "id_aluno")
    private Integer idAluno;

    @Id
    @SequenceGenerator(name = "seq_aluno_matricula2", sequenceName = "seq_aluno_matricula", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_aluno_matricula2")
    @Column(name = "matricula")
    private Integer matricula;

    @Column(name = "id_curso")
    private Integer idCurso;



}
