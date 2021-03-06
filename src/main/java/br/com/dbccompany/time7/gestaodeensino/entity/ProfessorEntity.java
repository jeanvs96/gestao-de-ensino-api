package br.com.dbccompany.time7.gestaodeensino.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Entity(name = "professor")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorEntity extends PessoaEntity{

    @Id
    @SequenceGenerator(name = "PROFESSOR_SEQ", sequenceName = "seq_professor", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFESSOR_SEQ")
    @Column(name = "id_professor")
    private Integer idProfessor;

    @Column(name = "registro_trabalho", updatable = false, insertable = false)
    private Integer registroTrabalho;

    @Column(name = "salario")
    private Double salario;

    @Column(name = "cargo")
    private String cargo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private EnderecoEntity enderecoEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "professorEntity",
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    private Set<DisciplinaEntity> disciplinaEntities;
}
