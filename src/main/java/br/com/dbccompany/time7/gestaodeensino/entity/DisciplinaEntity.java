package br.com.dbccompany.time7.gestaodeensino.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity(name = "disciplina")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaEntity {

    @Id
    @SequenceGenerator(name = "seq_disciplina2", sequenceName = "seq_disciplina", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_disciplina2")
    @Column(name = "id_disciplina")
    private Integer idDisciplina;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_professor", referencedColumnName = "id_professor")
    private ProfessorEntity professorEntity;
}
