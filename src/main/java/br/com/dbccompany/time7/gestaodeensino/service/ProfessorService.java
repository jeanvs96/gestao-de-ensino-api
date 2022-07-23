package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.*;
import br.com.dbccompany.time7.gestaodeensino.dto.endereco.EnderecoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.professor.ProfessorCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.professor.ProfessorDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.professor.ProfessorUpdateDTO;
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

        ProfessorEntity professorEntity = createToEntity(professorCreateDTO);

        if (professorCreateDTO.getIdDisciplina() != null){
        DisciplinaEntity disciplinaEntityRecuperada = disciplinaRepository.findById(professorCreateDTO.getIdDisciplina())
                .orElseThrow(() -> new RegraDeNegocioException("Disciplina não encontrada"));
        disciplinaEntityRecuperada.setProfessorEntity(professorEntity);
        professorEntity.setDisciplinaEntities(Set.of(disciplinaEntityRecuperada));
        }
        EnderecoEntity enderecoEntityRecuperado = enderecoService.findById(professorCreateDTO.getIdEndereco());
        professorEntity.setEnderecoEntity(enderecoEntityRecuperado);

        ProfessorDTO professorDTO = entityToDTO(professorRepository.save(professorEntity));

        log.info("Professor " + professorDTO.getNome() + " criado!");

        emailService.sendEmailCriarProfessor(professorDTO);

        return professorDTO;
    }

    public ProfessorDTO update(Integer idProfessor, ProfessorUpdateDTO professorAtualizar) throws RegraDeNegocioException {
        ProfessorEntity professorEntityRecuperado = findById(idProfessor);
        ProfessorEntity professorEntityAtualizar = updateToEntity(professorAtualizar);

        professorEntityAtualizar.setDisciplinaEntities(professorEntityRecuperado.getDisciplinaEntities());
        professorEntityAtualizar.setEnderecoEntity(enderecoService.findById(professorAtualizar.getIdEndereco()));
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
        professorRecuperado.getDisciplinaEntities().stream().forEach(disciplinaEntity -> {
            disciplinaEntity.setProfessorEntity(null);
            disciplinaEntity.setNotaEntities(disciplinaEntity.getNotaEntities());
            disciplinaEntity.setCursosEntities(disciplinaEntity.getCursosEntities());
            disciplinaRepository.save(disciplinaEntity);
        });

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

    public ProfessorEntity findById(Integer idProfessor) throws RegraDeNegocioException {
        return professorRepository.findById(idProfessor)
                .orElseThrow(() -> new RegraDeNegocioException("Professor não encontrado"));
    }

    public List<ProfessorEntity> findByName(String nome) {
        return professorRepository.findAllByNomeContainingIgnoreCase(nome);
    }

    public ProfessorEntity updateToEntity(ProfessorUpdateDTO professorUpdateDTO) {
        return objectMapper.convertValue(professorUpdateDTO, ProfessorEntity.class);
    }

    public ProfessorEntity createToEntity(ProfessorCreateDTO professorCreateDTO) {
        return objectMapper.convertValue(professorCreateDTO, ProfessorEntity.class);
    }

    public EnderecoDTO enderecoToEnderecoDTO(EnderecoEntity endereco) {
        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }

    public ProfessorDTO entityToDTO(ProfessorEntity professorEntity) {
        ProfessorDTO professorDTO = objectMapper.convertValue(professorEntity, ProfessorDTO.class);

        professorDTO.setEnderecoDTOS(enderecoToEnderecoDTO(professorEntity.getEnderecoEntity()));

        return professorDTO;
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
