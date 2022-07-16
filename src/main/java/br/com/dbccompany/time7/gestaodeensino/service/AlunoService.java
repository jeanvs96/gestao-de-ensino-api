//package br.com.dbccompany.time7.gestaodeensino.service;
//
//import br.com.dbccompany.time7.gestaodeensino.dto.AlunoCreateDTO;
//import br.com.dbccompany.time7.gestaodeensino.dto.AlunoDTO;
//import br.com.dbccompany.time7.gestaodeensino.entity.Aluno;
//import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
//import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.sql.SQLException;
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
//    public List<AlunoDTO> listarAlunos() {
//        try {
//            return alunoRepository.listar().stream()
//                    .map(aluno -> objectMapper.convertValue(aluno, AlunoDTO.class))
//                    .collect(Collectors.toList());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public AlunoDTO adicionarAluno(Integer id, AlunoCreateDTO aluno) {
//        try {
//            Aluno alunoEntity = objectMapper.convertValue(aluno, Aluno.class);
//            Aluno alunoCriado = alunoRepository.adicionar(alunoEntity);
//            //-----------------------
//            AlunoDTO alunoDTO = new AlunoDTO();
//            alunoDTO = objectMapper.convertValue(alunoCriado, AlunoDTO.class);
//            log.warn(("Aluno "+alunoDTO.getNome()+" criada"));
//            return alunoDTO;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public AlunoDTO editarAluno(Integer id, AlunoCreateDTO alunoAtualizar) {
//
//        AlunoDTO alunoDTO = null;
//        try {
//            log.info("Buscando o aluno...");
//            Aluno alunoEntity = objectMapper.convertValue(alunoAtualizar, Aluno.class);
//            Aluno alunoAtualizado = getAlunoById(id);
//            //-----------------------
//            alunoDTO = new AlunoDTO();
//            alunoDTO = objectMapper.convertValue(alunoAtualizado, AlunoDTO.class);
//            log.warn(("Aluno " + alunoDTO.getNome() + " alterada"));
//            alunoDTO.setNome(alunoAtualizar.getNome());
//            alunoDTO.setEmail(alunoAtualizar.getEmail());
//            alunoDTO.setTelefone(alunoAtualizar.getTelefone());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return alunoDTO;
//    }
//
//    public void removerAluno(Integer id) throws Exception{
//        try {
//            alunoRepository.listar().remove(getAlunoById(id));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Aluno getAlunoById(Integer id) throws Exception {
//        Aluno alunoRecuperado = null;
//        try {
//            alunoRecuperado = alunoRepository.listar().stream()
//                    .filter(aluno -> aluno.getIdAluno().equals(id))
//                    .findFirst()
//                    .orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado"));
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return alunoRecuperado;
//    }
//}