package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.CursoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.CursoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaXCursoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaXCursoDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Curso;
import br.com.dbccompany.time7.gestaodeensino.entity.DisciplinaXCurso;
import br.com.dbccompany.time7.gestaodeensino.entity.Nota;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.CursoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.DisciplinaXCursoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.NotaRepository;
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
    NotaRepository notaRepository;

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
                log.info("O curso " + cursoCreateDTO.getNome() + " já existe no banco");
                throw new RegraDeNegocioException("O curso já existe no banco de dados");
            }
        } catch (RegraDeNegocioException e) {
            log.info("Falha ao adicionar o curso");
            e.printStackTrace();
            throw new RegraDeNegocioException("Falha ao adicionar o curso");
        }
    }

    public CursoDTO put(Integer idCurso, CursoCreateDTO cursoCreateDTOAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando curso...");
        CursoDTO cursoDTO = objectMapper.convertValue(cursoCreateDTOAtualizar, CursoDTO.class);
        cursoDTO.setIdCurso(idCurso);

        if (cursoRepository.editar(idCurso, objectMapper.convertValue(cursoCreateDTOAtualizar, Curso.class))) {
            log.info(cursoDTO.getNome() + " atualizado");
            return cursoDTO;
        } else {
            log.info("Falha ao atualizar curso");
            throw new RegraDeNegocioException("Falha ao atualizar curso");
        }
    }

    public void delete(Integer idCurso) {
        log.info("Deletando o curso...");
        try {
            disciplinaXCursoRepository.removerPorIdCurso(idCurso);
            alunoRepository.removerPorIdCurso(idCurso);
            cursoRepository.remover(idCurso);
            log.info("Curso removido");
        } catch (RegraDeNegocioException e) {
            log.info("Falha ao remover curso");
            e.printStackTrace();
            e.getCause();
        }
    }

    public List<CursoDTO> list() throws RegraDeNegocioException {
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
        List<Nota> notas = alunoRepository.listByIdCurso(idCurso).stream()
                .map(aluno -> {
                    Nota nota = new Nota();
                    nota.setIdAluno(aluno.getIdAluno());
                    nota.setIdDisciplina(disciplinaXCursoEntity.getIdDisciplina());
                    return nota;
                }).toList();
        notas.stream().forEach(nota -> {
            try {
                notaRepository.adicionarNotasAluno(nota);
            } catch (RegraDeNegocioException e) {
                throw new RuntimeException(e);
            }
        });
        return disciplinaXCursoDTO;
    }

    public void deleteDisciplinaDoCurso(Integer idCurso, DisciplinaXCursoCreateDTO disciplinaXCursoCreateDTO) throws RegraDeNegocioException {
        disciplinaXCursoRepository.removerPorDisciplinaECurso(idCurso, disciplinaXCursoCreateDTO.getIdDisciplina());
    }

    public Curso containsCurso(CursoCreateDTO cursoCreateDTO) throws RegraDeNegocioException {
        Curso curso = cursoRepository.containsCurso(cursoCreateDTO);

        return curso;
    }
}
