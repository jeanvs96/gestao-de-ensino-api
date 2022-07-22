package br.com.dbccompany.time7.gestaodeensino.repository;

import br.com.dbccompany.time7.gestaodeensino.entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Integer> {

    List<ProfessorEntity> findAllByNomeContainingIgnoreCase(String nome);
}
