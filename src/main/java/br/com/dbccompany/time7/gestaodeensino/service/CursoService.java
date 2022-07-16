package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.CursoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.CursoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaXCursoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaXCursoDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Curso;
import br.com.dbccompany.time7.gestaodeensino.entity.DisciplinaXCurso;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.CursoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.DisciplinaXCursoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    DisciplinaXCursoRepository disciplinaXCursoRepository;

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    ObjectMapper objectMapper;


    public CursoDTO post(CursoCreateDTO cursoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando curso...");
        try {
            if (containsCurso(cursoCreateDTO).getIdCurso() == null) {
                Curso cursoEntity = objectMapper.convertValue(cursoCreateDTO, Curso.class);
                cursoEntity = cursoRepository.adicionar(cursoEntity);
                CursoDTO cursoDTO = objectMapper.convertValue(cursoEntity, CursoDTO.class);
                log.info("Curso " + cursoDTO.getNome() + " adicionado ao banco de dados");
                return cursoDTO;
            } else {
                throw new RegraDeNegocioException("O curso já existe no banco de dados");
            }
        } catch (SQLException e) {
            throw new RegraDeNegocioException("Falha ao adicionar o curso");
        }
    }

    public CursoDTO put(Integer idCurso, CursoCreateDTO cursoCreateDTOAtualizar) throws RegraDeNegocioException, SQLException {
        log.info("Atualizando curso...");
        CursoDTO cursoDTO = objectMapper.convertValue(cursoCreateDTOAtualizar, CursoDTO.class);
        cursoDTO.setIdCurso(idCurso);

        if (cursoRepository.editar(idCurso, objectMapper.convertValue(cursoCreateDTOAtualizar, Curso.class))) {
            return cursoDTO;
        } else {
            throw new RegraDeNegocioException("Falha ao atualizar curso");
        }
    }

    public void delete(Integer idCurso) {
        log.info("Deletando o curso...");

        try {
            disciplinaXCursoRepository.removerPorIdCurso(idCurso);
            alunoRepository.removerPorIdCurso(idCurso);
            cursoRepository.remover(idCurso);
        } catch (SQLException | RegraDeNegocioException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public List<CursoDTO> list() throws SQLException {
        log.info("Listando todos os cursos");
        return cursoRepository.listar().stream()
                .map(curso -> objectMapper.convertValue(curso, CursoDTO.class))
                .collect(Collectors.toList());
    }

    public DisciplinaXCursoDTO postDisciplinaNoCurso(Integer idCurso, DisciplinaXCursoCreateDTO disciplinaXCursoCreateDTO) throws RegraDeNegocioException, SQLException {
        DisciplinaXCursoDTO disciplinaXCursoDTO = objectMapper.convertValue(disciplinaXCursoCreateDTO, DisciplinaXCursoDTO.class);
        disciplinaXCursoDTO.setIdCurso(idCurso);

        DisciplinaXCurso disciplinaXCursoEntity = objectMapper.convertValue(disciplinaXCursoDTO, DisciplinaXCurso.class);

        disciplinaXCursoRepository.adicionarDisciplinaNoCurso(disciplinaXCursoEntity);

        return disciplinaXCursoDTO;
    }

    public void deleteDisciplinaDoCurso(Integer idCurso, DisciplinaXCursoCreateDTO disciplinaXCursoCreateDTO) throws SQLException {
        disciplinaXCursoRepository.removerPorDisciplinaECurso(idCurso, disciplinaXCursoCreateDTO.getIdDisciplina());
    }

    public Curso containsCurso(CursoCreateDTO cursoCreateDTO) throws SQLException {
        Curso curso = cursoRepository.containsCurso(cursoCreateDTO);

        return curso;
    }
}
