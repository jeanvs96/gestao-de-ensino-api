package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.AlunoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.AlunoDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Aluno;
import br.com.dbccompany.time7.gestaodeensino.entity.Professor;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired

    private ObjectMapper objectMapper;

    @Autowired
    private NotaService notaService;

    @Autowired
    private EmailService emailService;

    public List<AlunoDTO> listarAlunos() throws RegraDeNegocioException {
        log.info("Listando alunos");
        try {
            return alunoRepository.listar().stream()
                    .map(aluno -> objectMapper.convertValue(aluno, AlunoDTO.class))
                    .collect(Collectors.toList());
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Falha ao listar alunos");
        }
    }

    public AlunoDTO post(AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando aluno...");
        Aluno alunoEntity = objectMapper.convertValue(alunoCreateDTO, Aluno.class);
        try {
            alunoEntity = alunoRepository.adicionar(alunoEntity);
        } catch (RegraDeNegocioException e) {
            e.getCause();
            throw new RegraDeNegocioException("Falha ao criar aluno");
        }
        AlunoDTO alunoDTO  = objectMapper.convertValue(alunoEntity, AlunoDTO.class);
        notaService.adicionarNotasAluno(alunoDTO.getIdCurso(), alunoDTO.getIdAluno());
        emailService.sendEmailCriarAluno(alunoDTO);
        log.info("Aluno " + alunoDTO.getNome() + " criado");
        return alunoDTO;
    }

    public AlunoDTO put(Integer id, AlunoCreateDTO alunoAtualizar) throws RegraDeNegocioException {
        log.info("Atualizando aluno");
        AlunoDTO alunoDTO = objectMapper.convertValue(alunoAtualizar, AlunoDTO.class);
        alunoDTO.setIdAluno(id);

        if (alunoRepository.editar(id, objectMapper.convertValue(alunoAtualizar, Aluno.class))) {
            log.info(alunoDTO.getNome() + " teve seus dados atualizados");
            return alunoDTO;
        } else {
            throw new RegraDeNegocioException ("Falha ao atualizar o aluno");
        }
    }

    public void removerAluno(Integer id) throws RegraDeNegocioException{
        log.info("Removendo aluno");
        try {
            alunoRepository.listar().remove(getAlunoById(id));
            log.info("Aluno removido");
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Falha ao remover aluno");
        }
    }

    public Aluno getAlunoById(Integer id) throws RegraDeNegocioException {
        Aluno alunoRecuperado = alunoRepository.listar().stream()
                .filter(aluno -> aluno.getIdAluno().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado"));
        return alunoRecuperado;
    }
}