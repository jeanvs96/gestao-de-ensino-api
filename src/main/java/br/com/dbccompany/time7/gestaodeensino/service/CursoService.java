package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.CursoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.CursoDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.CursoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.DisciplinaEntity;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.CursoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    private final DisciplinaService disciplinaService;

    private final ObjectMapper objectMapper;


    public CursoDTO save(CursoCreateDTO cursoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando curso...");

        if (containsCurso(cursoCreateDTO).getIdCurso() == null) {

            CursoEntity cursoEntity = createToEntity(cursoCreateDTO);

            cursoEntity = cursoRepository.save(cursoEntity);

            CursoDTO cursoDTO = entityToDTO(cursoEntity);

            log.info("Curso " + cursoDTO.getNome() + " adicionado ao banco de dados");

            return cursoDTO;
        } else {
            log.info("O curso " + cursoCreateDTO.getNome() + " já existe no banco");

            throw new RegraDeNegocioException("O curso já existe no banco de dados");
        }
    }

    public CursoDTO update(Integer idCurso, CursoCreateDTO cursoCreateDTOAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando curso...");

        CursoEntity cursoEntityRecuperado = findById(idCurso);
        CursoEntity cursoEntityAtualizar = createToEntity(cursoCreateDTOAtualizar);

        cursoEntityAtualizar.setIdCurso(idCurso);
        cursoEntityAtualizar.setDisciplinasEntities(cursoEntityRecuperado.getDisciplinasEntities());
        cursoEntityAtualizar.setAlunosEntities(cursoEntityRecuperado.getAlunosEntities());

        cursoRepository.save(cursoEntityAtualizar);

        log.info(cursoEntityAtualizar.getNome() + " atualizado");

        return entityToDTO(cursoEntityAtualizar);
    }

    public void delete(Integer idCurso) throws RegraDeNegocioException {
        log.info("Deletando o curso...");

        CursoEntity cursoEntityRecuperado = findById(idCurso);

        cursoEntityRecuperado.setAlunosEntities(cursoEntityRecuperado.getAlunosEntities());
        cursoEntityRecuperado.setDisciplinasEntities(cursoEntityRecuperado.getDisciplinasEntities());

        cursoRepository.delete(cursoEntityRecuperado);

        log.info("Curso removido");
    }

    public List<CursoDTO> list() throws RegraDeNegocioException {
        log.info("Listando todos os cursos");
        return cursoRepository.findAll().stream()
                .map(this::entityToDTO)
                .toList();
    }

    public CursoDTO saveDisciplinaNoCurso(Integer idCurso, Integer idDisciplina) throws RegraDeNegocioException {
        log.info("Adicionado disciplina no curso");

        CursoEntity cursoEntityRecuperado = findById(idCurso);
        DisciplinaEntity disciplinaEntityRecuperada = disciplinaService.findById(idDisciplina);

        cursoEntityRecuperado.setAlunosEntities(cursoEntityRecuperado.getAlunosEntities());

        Set<DisciplinaEntity> disciplinasRecuperadas = cursoEntityRecuperado.getDisciplinasEntities();

        disciplinasRecuperadas.add(disciplinaEntityRecuperada);

        cursoEntityRecuperado.setDisciplinasEntities(disciplinasRecuperadas);

        return entityToDTO(cursoRepository.save(cursoEntityRecuperado));
    }

    public CursoDTO deleteDisciplinaDoCurso(Integer idCurso, Integer idDisciplina) throws RegraDeNegocioException {
        log.info("Removendo disciplina do curso");

        CursoEntity cursoEntityRecuperado = findById(idCurso);
        DisciplinaEntity disciplinaEntityRecuperada = disciplinaService.findById(idDisciplina);

        cursoEntityRecuperado.setAlunosEntities(cursoEntityRecuperado.getAlunosEntities());
        Set<DisciplinaEntity> disciplinasRecuperadas = cursoEntityRecuperado.getDisciplinasEntities();
        disciplinasRecuperadas.remove(disciplinaEntityRecuperada);

        cursoEntityRecuperado.setDisciplinasEntities(disciplinasRecuperadas);

        return entityToDTO(cursoRepository.save(cursoEntityRecuperado));
    }


    //Utilizacao interna
    public CursoEntity containsCurso(CursoCreateDTO cursoCreateDTO) throws RegraDeNegocioException {
        CursoEntity curso = cursoRepository.findByNomeContainingIgnoreCase(cursoCreateDTO.getNome());

        return curso;
    }

    public CursoEntity findById(Integer idCurso) throws RegraDeNegocioException {
        return cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RegraDeNegocioException("Curso não encontrado"));
    }

    public CursoDTO entityToDTO(CursoEntity cursoEntity) {
        return objectMapper.convertValue(cursoEntity, CursoDTO.class);
    }

    public CursoEntity createToEntity(CursoCreateDTO cursoCreateDTO) {
        return objectMapper.convertValue(cursoCreateDTO, CursoEntity.class);
    }
}
