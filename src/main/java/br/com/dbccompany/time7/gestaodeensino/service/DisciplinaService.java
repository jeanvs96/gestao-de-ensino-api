package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.disciplina.DisciplinaCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.disciplina.DisciplinaDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.professor.ProfessorComposeDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.DisciplinaEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.ProfessorEntity;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.DisciplinaRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.NotaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    private final NotaRepository notaRepository;
    private final ProfessorService professorService;
    private final ObjectMapper objectMapper;


    public DisciplinaDTO save(DisciplinaCreateDTO disciplinaCreateDTO) throws RegraDeNegocioException {
        log.info("Criando disciplina");

        if (containsDisciplina(disciplinaCreateDTO)) {
            throw new RegraDeNegocioException("A disciplina já existe no banco de dados");
        } else {
            DisciplinaEntity disciplinaEntity = createToEntity(disciplinaCreateDTO);

            if (disciplinaCreateDTO.getIdProfessor() != null){
                ProfessorEntity professorEntityRecuperado = professorService.findById(disciplinaCreateDTO.getIdProfessor());
                disciplinaEntity.setProfessorEntity(professorEntityRecuperado);
            }

            DisciplinaDTO disciplinaDTO = entityToDTO(disciplinaRepository.save(disciplinaEntity));

            log.info("Disciplina " + disciplinaEntity.getNome() + " criada");

            return disciplinaDTO;
        }
    }

    public DisciplinaDTO update(Integer idDisciplina, DisciplinaCreateDTO disciplinaCreateDTO) throws RegraDeNegocioException {
        log.info("Atualizando disciplina");

        if (containsDisciplina(disciplinaCreateDTO)) {
            log.info("O nome " + disciplinaCreateDTO.getNome() + " já existe no banco");

            throw new RegraDeNegocioException("Falha ao atualizar nome da disciplina, esse nome já existe no banco");
        } else {
            DisciplinaEntity disciplinaEntityRecuperada = findById(idDisciplina);
            DisciplinaEntity disciplinaEntityAtualizar = createToEntity(disciplinaCreateDTO);

            disciplinaEntityAtualizar.setIdDisciplina(idDisciplina);
            disciplinaEntityAtualizar.setNotaEntities(disciplinaEntityRecuperada.getNotaEntities());
            disciplinaEntityAtualizar.setProfessorEntity(professorService.findById(disciplinaCreateDTO.getIdProfessor()));
            disciplinaEntityAtualizar.setCursosEntities(disciplinaEntityRecuperada.getCursosEntities());

            DisciplinaDTO disciplinaDTO = entityToDTO(disciplinaRepository.save(disciplinaEntityAtualizar));

            log.info(disciplinaEntityAtualizar.getNome() + " atualizada");

            return disciplinaDTO;
        }
    }

    public void delete(Integer idDisciplina) throws RegraDeNegocioException {
        log.info("Removendo disciplina");

        DisciplinaEntity disciplinaEntityRecuperada = findById(idDisciplina);

        disciplinaEntityRecuperada.setCursosEntities(disciplinaEntityRecuperada.getCursosEntities());
        disciplinaEntityRecuperada.setProfessorEntity(disciplinaEntityRecuperada.getProfessorEntity());
        disciplinaEntityRecuperada.setNotaEntities(disciplinaEntityRecuperada.getNotaEntities());

        disciplinaRepository.delete(disciplinaEntityRecuperada);

        log.info("Disciplina removida");
    }

    public DisciplinaDTO addProfessorNaDisciplina(Integer idDisciplina, Integer idProfessor) throws RegraDeNegocioException {
        log.info("Adicionado professor na disciplina");

        DisciplinaEntity disciplinaEntityRecuperada = findById(idDisciplina);
        ProfessorEntity professorEntityRecuperado = professorService.findById(idProfessor);

        professorEntityRecuperado.setDisciplinaEntities(professorEntityRecuperado.getDisciplinaEntities());
        professorEntityRecuperado.setEnderecoEntity(professorEntityRecuperado.getEnderecoEntity());

        disciplinaEntityRecuperada.setCursosEntities(disciplinaEntityRecuperada.getCursosEntities());
        disciplinaEntityRecuperada.setNotaEntities(disciplinaEntityRecuperada.getNotaEntities());
        disciplinaEntityRecuperada.setProfessorEntity(professorEntityRecuperado);

        DisciplinaDTO disciplinaDTO = entityToDTO(disciplinaRepository.save(disciplinaEntityRecuperada));

        log.info("Professor adicionado na disciplina");

        return disciplinaDTO;
    }

    public DisciplinaDTO deleteProfessorDaDisciplina(Integer idDisciplina) throws RegraDeNegocioException {
        log.info("Removendo professor da disciplina");

        DisciplinaEntity disciplinaEntityRecuperada = findById(idDisciplina);

        disciplinaEntityRecuperada.setCursosEntities(disciplinaEntityRecuperada.getCursosEntities());
        disciplinaEntityRecuperada.setNotaEntities(disciplinaEntityRecuperada.getNotaEntities());
        disciplinaEntityRecuperada.setProfessorEntity(null);

        log.info("Professor removido da disciplina");

        return entityToDTO(disciplinaRepository.save(disciplinaEntityRecuperada));
    }

    public List<DisciplinaDTO> list() {
        log.info("Listando disciplinas...");

        return disciplinaRepository.findAll().stream()
                .map(disciplinaEntity -> {
                    DisciplinaDTO disciplinaDTO = entityToDTO(disciplinaEntity);
                    return disciplinaDTO;
                })
                .toList();
    }

    public DisciplinaDTO listByIdDisciplina(Integer idDisciplina) throws RegraDeNegocioException {
        log.info("Listando disciplina por ID");

        DisciplinaEntity disciplinaEntityRecuperada = findById(idDisciplina);
        DisciplinaDTO disciplinaDTO = entityToDTO(disciplinaEntityRecuperada);

        return disciplinaDTO;
    }


    // Utilização Interna

    public DisciplinaEntity findById(Integer idDisciplina) throws RegraDeNegocioException {
        return disciplinaRepository.findById(idDisciplina)
                .orElseThrow(() -> new RegraDeNegocioException("Disciplina não encontrada"));
    }

    public boolean containsDisciplina(DisciplinaCreateDTO disciplinaCreateDTO) throws RegraDeNegocioException {
        if (disciplinaRepository.findByNomeIgnoreCase(disciplinaCreateDTO.getNome()).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public DisciplinaEntity createToEntity(DisciplinaCreateDTO disciplinaCreateDTO) {
        return objectMapper.convertValue(disciplinaCreateDTO, DisciplinaEntity.class);
    }

    public DisciplinaDTO entityToDTO(DisciplinaEntity disciplinaEntity) {
        DisciplinaDTO disciplinaDTO = objectMapper.convertValue(disciplinaEntity, DisciplinaDTO.class);
        disciplinaDTO.setProfessor(objectMapper.convertValue(disciplinaEntity.getProfessorEntity(), ProfessorComposeDTO.class));
        return disciplinaDTO;
    }
}
