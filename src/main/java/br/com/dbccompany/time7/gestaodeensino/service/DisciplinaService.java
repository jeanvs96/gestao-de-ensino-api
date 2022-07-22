//package br.com.dbccompany.time7.gestaodeensino.service;
//
//import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaCreateDTO;
//import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaDTO;
//import br.com.dbccompany.time7.gestaodeensino.entity.DisciplinaEntity;
//import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
//import br.com.dbccompany.time7.gestaodeensino.repositoryOLD.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//@Slf4j
//public class DisciplinaService {
//
//    private final DisciplinaXCursoRepository disciplinaXCursoRepository;
//    private final DisciplinaRepository disciplinaRepository;
//
//    private final NotaRepository notaRepository;
//
//    private final ObjectMapper objectMapper;
//
//
//    public List<DisciplinaDTO> getDisciplinas() throws RegraDeNegocioException {
//        log.info("Listando disciplinas...");
//        try {
//            return disciplinaRepository.listar().stream()
//                    .map(disciplina -> disciplinaToDTO(disciplina)).toList();
//        } catch (RegraDeNegocioException e) {
//            throw new RegraDeNegocioException("Falha ao acessar o banco de dados");
//        }
//    }
//
//    public DisciplinaDTO getByIdDisciplina(Integer idDisciplina) throws RegraDeNegocioException {
//        log.info("Listando disciplina por ID");
//        return disciplinaToDTO(disciplinaRepository.listByIdDisciplina(idDisciplina));
//    }
//
//    public DisciplinaDTO postDisciplina(DisciplinaCreateDTO disciplinaCreateDTO) throws RegraDeNegocioException {
//        log.info("Criando disciplina");
//        try {
//            if (containsDisciplina(disciplinaCreateDTO).getIdDisciplina() == null) {
//                log.info("Disciplina " + disciplinaCreateDTO.getNome() + " criada");
//                return disciplinaToDTO(disciplinaRepository.adicionar(createToDisciplina(disciplinaCreateDTO)));
//            } else {
//                throw new RegraDeNegocioException("A disciplina já existe no banco de dados");
//            }
//        } catch (RegraDeNegocioException e) {
//            throw new RegraDeNegocioException("Falha ao adicionar a disciplina");
//        }
//
//    }
//
//    public DisciplinaDTO putDisciplina(Integer idDisciplina, DisciplinaCreateDTO disciplinaCreateDTO) throws RegraDeNegocioException {
//        log.info("Atualizando disciplina");
//        DisciplinaDTO disciplinaDTO = disciplinaToDTO(createToDisciplina(disciplinaCreateDTO));
//        disciplinaDTO.setIdDisciplina(idDisciplina);
//        if (disciplinaRepository.editar(idDisciplina, createToDisciplina(disciplinaCreateDTO))){
//            log.info(disciplinaDTO.getNome() + " foi atualizada no banco de dados");
//            return disciplinaDTO;
//        }else {
//            throw new RegraDeNegocioException("Falha ao atualizar disciplina");
//        }
//
//    }
//
//    public void deleteDisciplina(Integer idDisciplina) throws RegraDeNegocioException {
//        log.info("Removendo disciplina");
//        try {
//            disciplinaXCursoRepository.removerPorIdDisciplina(idDisciplina);
//            notaRepository.removerNotaPorIdDisciplina(idDisciplina);
//            disciplinaRepository.remover(idDisciplina);
//            log.info("Disciplina removida");
//        } catch (RegraDeNegocioException e) {
//            e.printStackTrace();
//            e.getCause();
//            throw new RegraDeNegocioException("Falha ao remover disciplina");
//        }
//    }
//
//    public void deleteProfessorDaDisciplina(Integer idDisciplina) throws RegraDeNegocioException {
//        log.info("Removendo professor da disciplina");
//        try {
//            disciplinaRepository.removerProfessor(idDisciplina);
//            log.info("Professor removido da disciplina");
//        } catch (RegraDeNegocioException e) {
//            throw new RegraDeNegocioException("Falha ao remover professor da disciplina");
//        }
//    }
//
//    public DisciplinaEntity containsDisciplina(DisciplinaCreateDTO disciplinaCreateDTO) throws RegraDeNegocioException {
//        return disciplinaRepository.containsDisciplina(disciplinaCreateDTO);
//    }
//
//    public DisciplinaEntity createToDisciplina(DisciplinaCreateDTO disciplinaCreateDTO) {
//        return objectMapper.convertValue(disciplinaCreateDTO, DisciplinaEntity.class);
//    }
//
//    public DisciplinaDTO disciplinaToDTO(DisciplinaEntity disciplina) {
//        return objectMapper.convertValue(disciplina, DisciplinaDTO.class);
//    }
//}
