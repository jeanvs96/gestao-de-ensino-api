package br.com.dbccompany.time7.gestaodeensino.dto.relatorios;


import br.com.dbccompany.time7.gestaodeensino.entity.DisciplinaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioProfessoresMenoresSalariosDTO {
    private Integer registroTrabalho;
    private String nomeProfessor;
    private Set<DisciplinaEntity> disciplinasEntities;
    private Double salario;
}
