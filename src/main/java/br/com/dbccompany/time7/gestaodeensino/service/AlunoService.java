package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.AlunoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.AlunoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.AlunoUpdateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.PageDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.AlunoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.CursoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.EnderecoEntity;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.NotaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;

    private final ObjectMapper objectMapper;

    //private final NotaService notaService;

    private final NotaRepository notaRepository;

    private final CursoService cursoService;
    private final EnderecoService enderecoService;
    private final EmailService emailService;


    public AlunoDTO save(AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando aluno...");

        EnderecoEntity enderecoEntity = enderecoService.findById(alunoCreateDTO.getIdEndereco());
        CursoEntity cursoEntity = cursoService.findById(alunoCreateDTO.getIdCurso());

        AlunoEntity alunoEntity = createToEntity(alunoCreateDTO);

        alunoEntity.setEnderecoEntity(enderecoEntity);
        alunoEntity.setCursoEntity(cursoEntity);

        AlunoDTO alunoDTO = entityToDTO(alunoEntity = alunoRepository.save(alunoEntity));

        //notaService.adicionarNotasAluno(alunoDTO.getIdCurso(), alunoDTO.getIdAluno());

        emailService.sendEmailCriarAluno(alunoDTO);

        log.info("Aluno " + alunoDTO.getNome() + " criado");

        return alunoDTO;
    }

    public AlunoDTO update(Integer idAluno, AlunoUpdateDTO alunoAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando aluno");

        AlunoEntity alunoEntityRecuperado = findById(idAluno);
        AlunoEntity alunoEntityAtualizar = updateToEntity(alunoAtualizar);

        alunoEntityAtualizar.setIdAluno(idAluno);
        alunoEntityAtualizar.setDisciplinaEntities(alunoEntityRecuperado.getDisciplinaEntities());
        alunoEntityAtualizar.setCursoEntity(alunoEntityRecuperado.getCursoEntity());
        alunoEntityAtualizar.setEnderecoEntity(alunoEntityRecuperado.getEnderecoEntity());

        alunoRepository.save(alunoEntityAtualizar);

//        if (alunoEntityRecuperado.getCursoEntity().getIdCurso() != alunoEntityAtualizar.getCursoEntity().getIdCurso()) {
//            notaRepository.removerNotaPorIdAluno(idAluno);
//            notaService.adicionarNotasAluno(alunoAtualizar.getIdCurso(), idAluno);
//        }

        AlunoDTO alunoDTO = entityToDTO(alunoEntityAtualizar);

        log.info(alunoDTO.getNome() + " teve seus dados atualizados");

        return alunoDTO;
    }

    public void delete(Integer idAluno) throws RegraDeNegocioException {
        log.info("Removendo aluno");

            AlunoEntity alunoEntityRecuperado = findById(idAluno);

            alunoEntityRecuperado.setDisciplinaEntities(alunoEntityRecuperado.getDisciplinaEntities());
            alunoEntityRecuperado.setCursoEntity(alunoEntityRecuperado.getCursoEntity());
            alunoEntityRecuperado.setEnderecoEntity(alunoEntityRecuperado.getEnderecoEntity());

            alunoRepository.delete(alunoEntityRecuperado);

            log.info("Aluno removido");
    }

    public List<AlunoDTO> list() throws RegraDeNegocioException {
        log.info("Listando alunos");
        return alunoRepository.findAll().stream()
                .map(this::entityToDTO)
                .toList();
    }

    public AlunoDTO listById(Integer idAluno) throws RegraDeNegocioException {
        log.info("Listando aluno por id");
        return entityToDTO(findById(idAluno));
    }


    //Utilização Interna

    public AlunoEntity createToEntity(AlunoCreateDTO alunoCreateDTO) {
        return objectMapper.convertValue(alunoCreateDTO, AlunoEntity.class);
    }

    public AlunoEntity updateToEntity(AlunoUpdateDTO alunoUpdateDTO) {
        return objectMapper.convertValue(alunoUpdateDTO, AlunoEntity.class);
    }

    public AlunoDTO entityToDTO(AlunoEntity alunoEntity) {
        return objectMapper.convertValue(alunoEntity, AlunoDTO.class);
    }

    public AlunoEntity findById(Integer id) throws RegraDeNegocioException {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado"));
    }

    public PageDTO<AlunoDTO> paginatedList(Integer pagina, Integer quantidadeDeRegistros) {
        PageRequest pageRequest = PageRequest.of(pagina, quantidadeDeRegistros);
        Page<AlunoEntity> page = alunoRepository.findAll(pageRequest);
        List<AlunoDTO> alunoDTOS = page.getContent().stream()
                .map(this::entityToDTO)
                .toList();
        return new PageDTO<>(page.getTotalElements(), page.getTotalPages(), pagina, quantidadeDeRegistros, alunoDTOS);
    }
}