package br.com.dbccompany.time7.gestaodeensino.repository;

import br.com.dbccompany.time7.gestaodeensino.entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Integer> {

    List<ProfessorEntity> findAllByNomeContainingIgnoreCase(String nome);

    @Query("select count (p)" +
            " from professor p" +
            " where p.enderecoEntity.idEndereco = :idEndereco")
    Integer countProfessorEntityByIdEndereco(@Param("idEndereco") Integer idEndereco);
}
