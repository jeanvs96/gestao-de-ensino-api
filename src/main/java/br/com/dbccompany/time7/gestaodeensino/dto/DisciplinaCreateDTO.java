package br.com.dbccompany.time7.gestaodeensino.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisciplinaCreateDTO {

    private String nome;
    private Integer idProfessor;
}
