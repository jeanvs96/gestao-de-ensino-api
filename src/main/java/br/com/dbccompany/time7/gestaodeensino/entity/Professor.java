package br.com.dbccompany.time7.gestaodeensino.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Professor extends Pessoa {

    private Integer idProfessor;

    private Integer registroTrabalho;

    private Integer idEndereco;

    private Double salario;

    private String cargo;
}
