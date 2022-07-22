package br.com.dbccompany.time7.gestaodeensino.entity;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorEntity {

    private Integer idProfessor;
    private String nome;
    private String telefone;
    private String email;
    private Integer idEndereco;
    private Integer registroTrabalho;
    private Double salario;

    private String cargo;
}
