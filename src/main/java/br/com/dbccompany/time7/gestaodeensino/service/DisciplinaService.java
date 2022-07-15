package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Disciplina;
import br.com.dbccompany.time7.gestaodeensino.entity.Endereco;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.*;
import br.com.dbccompany.time7.gestaodeensino.service.factory.CursoDisciplinaFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
@AllArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    private final ObjectMapper objectMapper;


    public List<Disciplina> listarDisciplina() {
        try {
            List<Disciplina> disciplinas = disciplinaRepository.listar();
            for (int i = 0; i < disciplinas.size(); i++) {
                System.out.println((i + 1) + " - " + disciplinas.get(i).getNome());
            }
            return disciplinas;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void adicionarDisciplina() {


    }

    public DisciplinaDTO updateDisciplina(Integer idDisciplina, DisciplinaCreateDTO disciplinaCreateDTO) throws SQLException, RegraDeNegocioException {
        DisciplinaDTO disciplinaDTO = disciplinaToDTO(createToDisciplina(disciplinaCreateDTO));
        disciplinaDTO.setIdDisciplina(idDisciplina);
        if (disciplinaRepository.editar(idDisciplina, createToDisciplina(disciplinaCreateDTO))){
            return disciplinaDTO;
        }else {
            throw new RegraDeNegocioException("Falha ao atualizar disciplina");
        }

    }

    public void removerDisciplina() {
        Integer escolhaDisciplina = 0;
        Integer disciplinaEscolhida = 0;
        Scanner scanner = new Scanner(System.in);
        DisciplinaXCursoRepository disciplinaXCursoRepository = new DisciplinaXCursoRepository();

        try {
            System.out.println("Qual disciplina deseja remover?");
            List<Disciplina> disciplinas = listarDisciplina();
            escolhaDisciplina = Integer.parseInt(scanner.nextLine()) - 1;
            disciplinaEscolhida = disciplinas.get(escolhaDisciplina).getIdDisciplina();
            disciplinaXCursoRepository.removerPorIdDisciplina(disciplinaEscolhida);
            NotaRepository notaRepository = new NotaRepository();
            notaRepository.removerNotaPorIdDisciplina(disciplinaEscolhida);
            disciplinaRepository.remover(disciplinaEscolhida);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public Optional<Disciplina> containsDisciplina(DisciplinaCreateDTO disciplinaCreateDTO) throws SQLException {
        Optional<Disciplina> disciplinaOptional = Optional.of(disciplinaRepository.containsDisciplina(disciplinaCreateDTO));

        return disciplinaOptional;
    }

    public void deleteProfessorOfDisciplina(Integer idDisciplina) throws SQLException, RegraDeNegocioException {
        Disciplina disciplinaRecuperada = findByIdDisciplina(idDisciplina);
        disciplinaRecuperada.setIdProfessor(null);
        disciplinaRepository.editar(idDisciplina, disciplinaRecuperada);
    }

    public Disciplina findByIdDisciplina(Integer idDisciplina) throws SQLException, RegraDeNegocioException {
        return disciplinaRepository.listar().stream()
                .filter(disciplina -> disciplina.getIdDisciplina().equals(idDisciplina))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Disciplina não encontrada"));
    }

    public Disciplina createToDisciplina(DisciplinaCreateDTO disciplinaCreateDTO) {
        return objectMapper.convertValue(disciplinaCreateDTO, Disciplina.class);
    }

    public DisciplinaDTO disciplinaToDTO(Disciplina disciplina) {
        return objectMapper.convertValue(disciplina, DisciplinaDTO.class);
    }
}
