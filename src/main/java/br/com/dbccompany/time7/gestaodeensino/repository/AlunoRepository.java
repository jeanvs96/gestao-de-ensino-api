package br.com.dbccompany.time7.gestaodeensino.repository;

import br.com.dbccompany.time7.gestaodeensino.dto.aluno.AlunoCompletoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.relatorios.RelatorioAlunosMaioresNotasDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.AlunoEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface AlunoRepository extends JpaRepository<AlunoEntity, Integer> {

    @Query("select count (a)" +
            " from aluno a" +
            " where a.enderecoEntity.idEndereco = :idEndereco")
    Integer countProfessorEntityByIdEndereco(@Param("idEndereco") Integer idEndereco);


    @Query(value = "select new br.com.dbccompany.time7.gestaodeensino.dto.relatorios.RelatorioAlunosMaioresNotasDTO (" +
            "a.nome, " +
            "a.matricula, " +
            "dE.nome, " +
            "c.nome, " +
            "n.media " +
            ") " +
            "from aluno a " +
            "left join a.cursoEntity c " +
            "left join a.notaEntities n " +
            "left join n.disciplinaEntity dE " +
            "ORDER BY n.media DESC, dE.nome")
    List<RelatorioAlunosMaioresNotasDTO> relatorioAlunoNota();


    @Query(value = """
           select new br.com.dbccompany.time7.gestaodeensino.dto.aluno.AlunoCompletoDTO(
           a.nome,
           a.telefone,
           a.email,
           a.matricula,
           curso.nome,
           e.logradouro,
           e.numero,
           e.cep,
           e.cidade,
           e.estado)
           from aluno a
           left join a.cursoEntity curso
           left join a.enderecoEntity e
           order by a.matricula
           """)
    Page<AlunoCompletoDTO> exibirAlunoCompleto(Pageable pageable);
}
