package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.CursoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.CursoDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Curso;
import br.com.dbccompany.time7.gestaodeensino.entity.Disciplina;
import br.com.dbccompany.time7.gestaodeensino.entity.DisciplinaXCurso;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.CursoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.DisciplinaRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.DisciplinaXCursoRepository;
import br.com.dbccompany.time7.gestaodeensino.service.factory.CursoDisciplinaFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
@Slf4j
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    ObjectMapper objectMapper;


    public CursoDTO post(CursoCreateDTO cursoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando curso...");
        try {
            if (containsCurso(cursoCreateDTO).isEmpty()) {
                Curso cursoEntity = objectMapper.convertValue(cursoCreateDTO, Curso.class);
                cursoEntity = cursoRepository.adicionar(cursoEntity);
                CursoDTO cursoDTO = objectMapper.convertValue(cursoEntity, CursoDTO.class);
                log.info("Curso " + cursoDTO.getNome() + " adicionado ao banco de dados");
                return cursoDTO;
            } else {
                throw new RegraDeNegocioException("O curso já existe no banco de dados");
            }
        } catch (SQLException e) {
            throw new RegraDeNegocioException("Falha ao adicionar o curso");
        }
    }


    public CursoDTO put(Integer id, CursoCreateDTO cursoCreateDTOAtualizar) {
        log.info("Atualizando curso...");

        try {
            if (cursoRepository.conferirIdCurso(id)) {
                controleNome = Integer.parseInt(scanner.nextLine());
                if (controleNome == 1) {
                    System.out.println("Informe o novo Nome do Curso: ");
                    cursoEscolhido.setNome(scanner.nextLine());
                } else {
                    System.out.println("Nome atual do Curso: " + cursoEscolhido.getNome());
                }

                cursoRepository.editar(cursoEscolhido.getIdCurso(), cursoEscolhido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerCurso() {
        Integer escolhaCurso = 0;
        Integer cursoEscolhido = 0;
        Scanner scanner = new Scanner(System.in);
        DisciplinaXCursoRepository disciplinaXCursoRepository = new DisciplinaXCursoRepository();
        AlunoRepository alunoRepository = new AlunoRepository();


        try {
            System.out.println("Qual curso deseja remover?");
            List<Curso> cursos = listarCurso();
            escolhaCurso = Integer.parseInt(scanner.nextLine()) - 1;
            cursoEscolhido = cursos.get(escolhaCurso).getIdCurso();
            disciplinaXCursoRepository.removerPorIdCurso(cursoEscolhido);
            alunoRepository.removerPorIdCurso(cursoEscolhido);
            cursoRepository.remover(cursoEscolhido);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public List<Curso> listarCurso() {
        try {
            List<Curso> cursos = cursoRepository.listar();
            for (int i = 0; i < cursos.size(); i++) {
                System.out.println((i + 1) + " - " + cursos.get(i).getNome());
            }
            return cursos;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void adicionarDisciplinaNoCurso() {
        int escolhaCurso = 0;
        int escolhaDisciplina = 0;
        int opcao = 0;
        Scanner scanner = new Scanner(System.in);
        DisciplinaService disciplinaService = new DisciplinaService();
        DisciplinaXCurso disciplinaXCurso = new DisciplinaXCurso();
        DisciplinaXCursoRepository disciplinaXCursoRepository = new DisciplinaXCursoRepository();
        try {
            System.out.println("Escolha o curso: ");
            List<Curso> cursos = listarCurso();
            escolhaCurso = Integer.parseInt(scanner.nextLine());
            disciplinaXCurso.setIdCurso(cursos.get(escolhaCurso - 1).getIdCurso());

            while (opcao != 2) {
                System.out.println("Escolha a disciplina a ser adicionada: ");
                List<Disciplina> disciplinas = disciplinaService.listarDisciplina();
                escolhaDisciplina = Integer.parseInt(scanner.nextLine()) - 1;

                disciplinaXCurso.setIdDisciplina(disciplinas.get(escolhaDisciplina).getIdDisciplina());
                disciplinaXCursoRepository.adicionarDisciplinaNoCurso(disciplinaXCurso);

                System.out.println("Deseja adicionar outra disciplina no mesmo curso? [1 - Sim  2 - Não]");
                opcao = Integer.parseInt(scanner.nextLine());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerDisciplinaDoCurso() {
        int escolhaCurso = 0;
        int escolhaDisciplina = 0;
        int opcao = 0;
        Scanner scanner = new Scanner(System.in);
        DisciplinaRepository disciplinaRepository = new DisciplinaRepository();
        DisciplinaXCursoRepository disciplinaXCursoRepository = new DisciplinaXCursoRepository();
        List<Disciplina> disciplinasDoCurso;
        try {
            System.out.println("Escolha o curso:");
            List<Curso> cursos = listarCurso();
            escolhaCurso = Integer.parseInt(scanner.nextLine()) - 1;

            while (opcao != 2) {
                System.out.println("Escolha a disciplina a ser removida:");
                disciplinasDoCurso = disciplinaRepository.listarPorId(disciplinaXCursoRepository.listarPorCurso(cursos.get(escolhaCurso).getIdCurso()));
                for (int i = 0; i < disciplinasDoCurso.size(); i++) {
                    System.out.println((i + 1) + " - " + disciplinasDoCurso.get(i).getNome());
                }
                escolhaDisciplina = (Integer.parseInt(scanner.nextLine())) - 1;
                disciplinaXCursoRepository.removerDisciplinaDoCurso(cursos.get(escolhaCurso).getIdCurso(), disciplinasDoCurso.get(escolhaDisciplina).getIdDisciplina());
                System.out.println("Remover outra disciplina do curso? [1 - Sim  2 - Não]");
                opcao = Integer.parseInt(scanner.nextLine());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Curso> containsCurso(CursoCreateDTO cursoCreateDTO) throws SQLException {
        Optional<Curso> cursoOptional = Optional.of(cursoRepository.containsCurso(cursoCreateDTO));

        return cursoOptional;
    }
}
