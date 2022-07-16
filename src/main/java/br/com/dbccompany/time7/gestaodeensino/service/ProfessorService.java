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


    public ProfessorDTO post(ProfessorCreateDTO professorCreateDTO) {
        log.info("Criando o professor...");

        Professor professorEntity = objectMapper.convertValue(professorCreateDTO, Professor.class);
        try {
            professorEntity = professorRepository.adicionar(professorEntity);
        } catch (SQLException e) {
            e.getCause();
        }

        ProfessorDTO professorDTO = objectMapper.convertValue(professorEntity, ProfessorDTO.class);
        log.info("Professor " + professorDTO.getNome() + " criado!");

        return professorDTO;
    }

    public ProfessorDTO put(Integer id, ProfessorCreateDTO professorAtualizar) throws SQLException {
        log.info("Atualizando o professor...");
        Professor professorEntity = findByIdProfessor(id);

        professorEntity.setNome(professorAtualizar.getNome());
        professorEntity.setCargo(professorAtualizar.getCargo());
        professorEntity.setEmail(professorAtualizar.getEmail());
        professorEntity.setTelefone(professorAtualizar.getTelefone());
        professorEntity.setRegistroTrabalho(professorAtualizar.getRegistroTrabalho());
        professorEntity.setSalario(professorAtualizar.getSalario());
        professorEntity.setIdEndereco(professorAtualizar.getIdEndereco());

        try {
            professorRepository.editar(id, professorEntity);
        } catch (SQLException e) {
            e.getCause();
        }

        ProfessorDTO professorDTO = objectMapper.convertValue(professorEntity, ProfessorDTO.class);
        log.info(professorDTO.getNome() + " teve seus dados atualizados");
        return professorDTO;
    }

    public void delete(Integer id) throws SQLException {
        log.info("Deletando o professor...");
        Professor professorRecuperado = findByIdProfessor(id);

        try {
            enderecoService.deleteEndereco(professorRecuperado.getIdEndereco());
            disciplinaService.deleteProfessorDaDisciplina(professorRecuperado.getIdProfessor());
            professorRepository.remover(id);
        } catch (SQLException | RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    public List<ProfessorDTO> list() throws SQLException {
        log.info("Listando todos professores");
        return professorRepository.listar().stream()
                    .map(professor -> objectMapper.convertValue(professor, ProfessorDTO.class))
                    .collect(Collectors.toList());

    }

    public ProfessorDTO listById(Integer idProfessor) throws SQLException {
        log.info("Listando professor por id");
        return objectMapper.convertValue(findByIdProfessor(idProfessor), ProfessorDTO.class);
    }

    public List<ProfessorDTO> listByName(String nomeProfessor) throws SQLException, RegraDeNegocioException {
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
    public Professor findByIdProfessor(Integer idProfessor) throws SQLException {
        return professorRepository.professorPorId(idProfessor);
    }

    public List<Professor> findByNameProfessor(String nome) throws SQLException {
        return professorRepository.listar().stream()
                .filter(pessoa -> pessoa.getNome().toUpperCase().contains(nome.toUpperCase()))
                .collect(Collectors.toList());
    }

}
