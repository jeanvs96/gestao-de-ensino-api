//package br.com.dbccompany.time7.gestaodeensino.service;
//
//import br.com.dbccompany.time7.gestaodeensino.dto.AlunoCreateDTO;
//import br.com.dbccompany.time7.gestaodeensino.dto.AlunoDTO;
//import br.com.dbccompany.time7.gestaodeensino.dto.AlunoUpdateDTO;
//import br.com.dbccompany.time7.gestaodeensino.entity.AlunoEntity;
//import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
//import br.com.dbccompany.time7.gestaodeensino.repositoryOLD.AlunoRepository;
//import br.com.dbccompany.time7.gestaodeensino.repositoryOLD.NotaRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class AlunoService {
//    @Autowired
//    private AlunoRepository alunoRepository;
//
//    @Autowired
//
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private NotaService notaService;
//
//    @Autowired
//    private NotaRepository notaRepository;
//
//    @Autowired
//    private EnderecoService enderecoService;
//
//    @Autowired
//    private EmailService emailService;
//
//    public List<AlunoDTO> listarAlunos() throws RegraDeNegocioException {
//        log.info("Listando alunos");
//        try {
//            return alunoRepository.listar().stream()
//                    .map(aluno -> objectMapper.convertValue(aluno, AlunoDTO.class))
//                    .collect(Collectors.toList());
//        } catch (RegraDeNegocioException e) {
//            e.printStackTrace();
//            throw new RegraDeNegocioException("Falha ao listar alunos");
//        }
//    }
//
//    public AlunoDTO post(AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
//        log.info("Criando aluno...");
//        AlunoEntity alunoEntity = objectMapper.convertValue(alunoCreateDTO, AlunoEntity.class);
//        try {
//            alunoEntity = alunoRepository.adicionar(alunoEntity);
//        } catch (RegraDeNegocioException e) {
//            e.getCause();
//            throw new RegraDeNegocioException("Falha ao criar aluno");
//        }
//        AlunoDTO alunoDTO = objectMapper.convertValue(alunoEntity, AlunoDTO.class);
//        notaService.adicionarNotasAluno(alunoDTO.getIdCurso(), alunoDTO.getIdAluno());
//        emailService.sendEmailCriarAluno(alunoDTO);
//        log.info("Aluno " + alunoDTO.getNome() + " criado");
//        return alunoDTO;
//    }
//
//    public AlunoDTO put(Integer idAluno, AlunoUpdateDTO alunoAtualizar) throws RegraDeNegocioException {
//        log.info("Atualizando aluno");
//
//        AlunoEntity alunoEntityRecuperado = alunoRepository.listByIdAluno(idAluno);
//        alunoRepository.editar(idAluno, objectMapper.convertValue(alunoAtualizar, AlunoEntity.class));
//        AlunoEntity alunoEntityAtualizado = alunoRepository.listByIdAluno(idAluno);
//
//        if (alunoEntityRecuperado.getIdCurso() != alunoEntityAtualizado.getIdCurso()) {
//            notaRepository.removerNotaPorIdAluno(idAluno);
//            notaService.adicionarNotasAluno(alunoAtualizar.getIdCurso(), idAluno);
//        }
//
//        AlunoDTO alunoDTO = objectMapper.convertValue(alunoEntityAtualizado, AlunoDTO.class);
//        log.info(alunoDTO.getNome() + " teve seus dados atualizados");
//
//        return alunoDTO;
//    }
//
//    public void removerAluno(Integer id) throws RegraDeNegocioException {
//        log.info("Removendo aluno");
//        try {
//            AlunoEntity alunoEntityRecuperado = alunoRepository.listByIdAluno(id);
//            notaRepository.removerNotaPorIdAluno(id);
//            alunoRepository.remover(id);
//            enderecoService.deleteEndereco(alunoEntityRecuperado.getIdEndereco());
//            log.info("Aluno removido");
//        } catch (RegraDeNegocioException e) {
//            e.printStackTrace();
//            throw new RegraDeNegocioException("Falha ao remover aluno");
//        }
//    }
//
//    public AlunoEntity getAlunoById(Integer id) throws RegraDeNegocioException {
//        return alunoRepository.listByIdAluno(id);
//    }
//}