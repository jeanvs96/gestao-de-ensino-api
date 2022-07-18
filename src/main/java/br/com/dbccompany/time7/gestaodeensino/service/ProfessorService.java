package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.ProfessorCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.ProfessorDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Professor;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.ProfessorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailService emailService;


    public ProfessorDTO post(ProfessorCreateDTO professorCreateDTO) {
        log.info("Criando o professor...");

        Professor professorEntity = objectMapper.convertValue(professorCreateDTO, Professor.class);
        try {
            professorEntity = professorRepository.adicionar(professorEntity);
        } catch (RegraDeNegocioException e) {
            e.getCause();
        }

        ProfessorDTO professorDTO = objectMapper.convertValue(professorEntity, ProfessorDTO.class);
        log.info("Professor " + professorDTO.getNome() + " criado!");
        emailService.sendEmailCriarProfessor(professorDTO);

        return professorDTO;
    }

    public ProfessorDTO put(Integer id, ProfessorCreateDTO professorAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando o professor...");
        ProfessorDTO professorDTO = objectMapper.convertValue(professorAtualizar, ProfessorDTO.class);
        professorDTO.setIdProfessor(id);

        if (professorRepository.editar(id, objectMapper.convertValue(professorAtualizar, Professor.class))) {
            log.info(professorDTO.getNome() + " teve seus dados atualizados");
            return professorDTO;
        } else {
            throw new RegraDeNegocioException("Falha ao atualizar o professor");
        }
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        log.info("Deletando o professor...");
        Professor professorRecuperado = findByIdProfessor(id);

        try {
            enderecoService.deleteEndereco(professorRecuperado.getIdEndereco());
            disciplinaService.deleteProfessorDaDisciplina(professorRecuperado.getIdProfessor());
            professorRepository.remover(id);
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    public List<ProfessorDTO> list() throws RegraDeNegocioException {
        log.info("Listando todos professores");
        return professorRepository.listar().stream()
                    .map(professor -> objectMapper.convertValue(professor, ProfessorDTO.class))
                    .collect(Collectors.toList());

    }

    public ProfessorDTO listById(Integer idProfessor) throws RegraDeNegocioException {
        log.info("Listando professor por id");
        return objectMapper.convertValue(findByIdProfessor(idProfessor), ProfessorDTO.class);
    }

    public List<ProfessorDTO> listByName(String nomeProfessor) throws RegraDeNegocioException {
        log.info("Listando professor por nome");
        if (findByNameProfessor(nomeProfessor).isEmpty()) {
            log.info("Nome não encontrado");
            throw new RegraDeNegocioException("Nome não encontrado");
        } else {
            log.info("Nome encontrado");
            return findByNameProfessor(nomeProfessor).stream()
                    .map(professor -> objectMapper.convertValue(professor, ProfessorDTO.class))
                    .collect(Collectors.toList());
        }
    }


    //Utilização Interna
    public Professor findByIdProfessor(Integer idProfessor) throws RegraDeNegocioException {
        return professorRepository.professorPorId(idProfessor);
    }

    public List<Professor> findByNameProfessor(String nome) throws RegraDeNegocioException {
        return professorRepository.listar().stream()
                .filter(pessoa -> pessoa.getNome().toUpperCase().contains(nome.toUpperCase()))
                .collect(Collectors.toList());
    }

}
