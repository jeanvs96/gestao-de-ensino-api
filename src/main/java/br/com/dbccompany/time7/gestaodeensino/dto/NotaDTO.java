package br.com.dbccompany.time7.gestaodeensino.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotaDTO {

    private Integer idNota;
    private Integer idDisciplina;
    private Integer idAluno;
    private Double nota1;
    private Double nota2;
    private Double nota3;
    private Double nota4;
    private Double media;
}
