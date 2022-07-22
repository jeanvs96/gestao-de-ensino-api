package br.com.dbccompany.time7.gestaodeensino.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "aluno")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoEntity extends PessoaEntity{

    @Id
    @SequenceGenerator(name = "seq_aluno2", sequenceName = "seq_aluno", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_aluno2")
    @Column(name = "id_aluno")
    private Integer idAluno;

    @Column(name = "matricula", updatable = false, insertable = false)
    private Integer matricula;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    private CursoEntity cursoEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private EnderecoEntity enderecoEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "alunoEntity",
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE,
            orphanRemoval = true)
    private Set<NotaEntity> notaEntities;
}
