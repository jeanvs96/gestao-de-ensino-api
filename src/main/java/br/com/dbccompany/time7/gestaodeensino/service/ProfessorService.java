package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.*;
import br.com.dbccompany.time7.gestaodeensino.entity.AlunoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.DisciplinaEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.EnderecoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.ProfessorEntity;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.DisciplinaRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.ProfessorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ObjectMapper objectMapper;
    private final EnderecoService enderecoService;

    private final DisciplinaRepository disciplinaRepository;
    private final EmailService emailService;


    public ProfessorDTO save(ProfessorCreateDTO professorCreateDTO) throws RegraDeNegocioException {
        log.info("Criando o professor...");

        EnderecoEntity enderecoEntityRecuperado = enderecoService.findById(professorCreateDTO.getIdEndereco());
        DisciplinaEntity disciplinaEntityRecuperada = disciplinaRepository.findById(professorCreateDTO.getIdDisciplina())
                .orElseThrow(() -> new RegraDeNegocioException("Disciplina não encontrada"));

        ProfessorEntity professorEntity = createToEntity(professorCreateDTO);

        professorEntity.setDisciplinaEntities(Set.of(disciplinaEntityRecuperada));
        professorEntity.setEnderecoEntity(enderecoEntityRecuperado);

        professorEntity = professorRepository.save(professorEntity);

        ProfessorDTO professorDTO = entityToDTO(professorEntity);

        log.info("Professor " + professorDTO.getNome() + " criado!");

        emailService.sendEmailCriarProfessor(professorDTO);

        return professorDTO;
    }

    public ProfessorDTO update(Integer idProfessor, ProfessorUpdateDTO professorAtualizar) throws RegraDeNegocioException {
        ProfessorEntity professorEntityRecuperado = findById(idProfessor);
        ProfessorEntity professorEntityAtualizar = updateToEntity(professorAtualizar);

        professorEntityAtualizar.setDisciplinaEntities(professorEntityRecuperado.getDisciplinaEntities());
        professorEntityAtualizar.setEnderecoEntity(professorEntityRecuperado.getEnderecoEntity());
        professorEntityAtualizar.setIdProfessor(idProfessor);

        log.info("Atualizando o professor...");
        ProfessorDTO professorDTO = entityToDTO(professorRepository.save(professorEntityAtualizar));
        log.info("Professor atualizado");

        return professorDTO;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        log.info("Deletando o professor...");
        ProfessorEntity professorRecuperado = findById(id);

        professorRecuperado.setEnderecoEntity(professorRecuperado.getEnderecoEntity());
        professorRecuperado.setDisciplinaEntities(professorRecuperado.getDisciplinaEntities());

        professorRepository.delete(professorRecuperado);

        log.info(professorRecuperado.getNome() + " removido do banco de dados");
    }

    public List<ProfessorDTO> list() throws RegraDeNegocioException {
        log.info("Listando todos professores");
        return professorRepository.findAll().stream()
                    .map(this::entityToDTO)
                    .toList();
    }

    public ProfessorDTO listById(Integer idProfessor) throws RegraDeNegocioException {
        log.info("Listando professor por id");

        return entityToDTO(findById(idProfessor));
    }

    public List<ProfessorDTO> listByName(String nomeProfessor) throws RegraDeNegocioException {
        return findByName(nomeProfessor).stream()
                .map(this::entityToDTO)
                .toList();
    }


    //Utilização Interna

    public ProfessorEntity updateToEntity(ProfessorUpdateDTO professorUpdateDTO) {
        return objectMapper.convertValue(professorUpdateDTO, ProfessorEntity.class);
    }

    public ProfessorEntity createToEntity(ProfessorCreateDTO professorCreateDTO) {
        return objectMapper.convertValue(professorCreateDTO, ProfessorEntity.class);
    }

    public ProfessorDTO entityToDTO(ProfessorEntity professorEntity) {
        return objectMapper.convertValue(professorEntity, ProfessorDTO.class);
    }

    public ProfessorEntity findById(Integer idProfessor) throws RegraDeNegocioException {
        return professorRepository.findById(idProfessor)
                .orElseThrow(() -> new RegraDeNegocioException("Professor não encontrado"));
    }

    public List<ProfessorEntity> findByName(String nome) {
        return professorRepository.findAllByNomeContainingIgnoreCase(nome);
    }

    public PageDTO<ProfessorDTO> paginatedList(Integer pagina, Integer quantidadeDeRegistros) {
        PageRequest pageRequest = PageRequest.of(pagina, quantidadeDeRegistros);
        Page<ProfessorEntity> page = professorRepository.findAll(pageRequest);
        List<ProfessorDTO> alunoDTOS = page.getContent().stream()
                .map(this::entityToDTO)
                .toList();
        return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, quantidadeDeRegistros, alunoDTOS);

    }
}
