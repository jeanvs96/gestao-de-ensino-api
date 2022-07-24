package br.com.dbccompany.time7.gestaodeensino.dto.relatorios;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioProfessoresMenoresSalariosDTO {
    private Integer registroTrabalho;
    private String nomeProfessor;
    private Double salario;
}
