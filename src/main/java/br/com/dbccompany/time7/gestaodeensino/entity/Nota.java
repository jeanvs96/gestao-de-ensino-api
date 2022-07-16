package br.com.dbccompany.time7.gestaodeensino.entity;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Nota {

    private Integer idNota;

    private Integer idDisciplina;

    private Integer idAluno;

    private Double nota1;

    private Double nota2;

    private Double nota3;

    private Double nota4;

    private Double media;
}
