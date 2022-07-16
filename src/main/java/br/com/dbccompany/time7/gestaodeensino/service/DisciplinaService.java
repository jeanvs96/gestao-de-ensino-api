package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Disciplina;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DisciplinaService {

    private final DisciplinaXCursoRepository disciplinaXCursoRepository;
    private final DisciplinaRepository disciplinaRepository;

    private final ObjectMapper objectMapper;


    public List<DisciplinaDTO> getDisciplinas() throws RegraDeNegocioException {
        try {
            return disciplinaRepository.listar().stream()
                    .map(disciplina -> disciplinaToDTO(disciplina)).toList();
        } catch (SQLException e) {
            throw new RegraDeNegocioException("Falha ao acessar o banco de dados");
        }
    }

    public DisciplinaDTO getByIdDisciplina(Integer idDisciplina) throws RegraDeNegocioException {
        return disciplinaToDTO(disciplinaRepository.listByIdDisciplina(idDisciplina));
    }

    public DisciplinaDTO postDisciplina(DisciplinaCreateDTO disciplinaCreateDTO) throws RegraDeNegocioException {
        try {
            if (containsDisciplina(disciplinaCreateDTO).getIdDisciplina() == null) {
                return disciplinaToDTO(disciplinaRepository.adicionar(createToDisciplina(disciplinaCreateDTO)));
            } else {
                throw new RegraDeNegocioException("A disciplina já existe no banco de dados");
            }
        } catch (SQLException e) {
            throw new RegraDeNegocioException("Falha ao adicionar a disciplina");
        }

    }

    public DisciplinaDTO putDisciplina(Integer idDisciplina, DisciplinaCreateDTO disciplinaCreateDTO) throws SQLException, RegraDeNegocioException {
        DisciplinaDTO disciplinaDTO = disciplinaToDTO(createToDisciplina(disciplinaCreateDTO));
        disciplinaDTO.setIdDisciplina(idDisciplina);
        if (disciplinaRepository.editar(idDisciplina, createToDisciplina(disciplinaCreateDTO))){
            return disciplinaDTO;
        }else {
            throw new RegraDeNegocioException("Falha ao atualizar disciplina");
        }

    }

    public void deleteDisciplina(Integer idDisciplina) throws RegraDeNegocioException {
        try {
            disciplinaXCursoRepository.removerPorIdDisciplina(idDisciplina);
            disciplinaRepository.remover(idDisciplina);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
            throw new RegraDeNegocioException("Falha ao remover disciplina");
        }
    }

    public void deleteProfessorDaDisciplina(Integer idDisciplina) throws RegraDeNegocioException {
        try {
            disciplinaRepository.removerProfessor(idDisciplina);
        } catch (SQLException e) {
            throw new RegraDeNegocioException("Falha ao remover professor da disciplina");
        }
    }

    public Disciplina containsDisciplina(DisciplinaCreateDTO disciplinaCreateDTO) throws RegraDeNegocioException {
        return disciplinaRepository.containsDisciplina(disciplinaCreateDTO);
    }

    public Disciplina createToDisciplina(DisciplinaCreateDTO disciplinaCreateDTO) {
        return objectMapper.convertValue(disciplinaCreateDTO, Disciplina.class);
    }

    public DisciplinaDTO disciplinaToDTO(Disciplina disciplina) {
        return objectMapper.convertValue(disciplina, DisciplinaDTO.class);
    }
}
