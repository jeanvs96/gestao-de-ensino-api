package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.entity.Curso;
import br.com.dbccompany.time7.gestaodeensino.entity.Disciplina;
import br.com.dbccompany.time7.gestaodeensino.entity.DisciplinaXCurso;
import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.CursoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.DisciplinaRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.DisciplinaXCursoRepository;
import br.com.dbccompany.time7.gestaodeensino.service.factory.CursoDisciplinaFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CursoService {

    CursoRepository cursoRepository;

    public CursoService() {
        cursoRepository = new CursoRepository();
    }

    public void adicionarCurso() {

        Curso curso = CursoDisciplinaFactory.criarCurso();

        try {
            if (!(this.conferirSeCursoExiste(curso))) {
                cursoRepository.adicionar(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public Boolean conferirSeCursoExiste(Curso curso) throws SQLException {
        Boolean controle = false;
        int posicao = 0;
        List<Curso> cursosDb = cursoRepository.listar();
        for (Curso db : cursosDb) {
            controle = db.getNome().equals(curso.getNome());
            if (controle) {
                posicao = db.getIdCurso();
            }
        }
        if (posicao != 0) {
            curso.setIdCurso(posicao);
            return true;
        }
        return false;
    }

    public void atualizarCurso() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o numero referente ao Curso que deseja atualizar: ");

        int controleNome = 0;
        Integer escolhaCurso = 0;
        listarCurso();
        escolhaCurso = Integer.parseInt(scanner.nextLine());
        Curso cursoEscolhido = listarCurso().get(escolhaCurso - 1);

        try {
            if (cursoRepository.conferirIdCurso(cursoEscolhido.getIdCurso())) {
                System.out.println("Atualizar nome do Curso? [1 - Sim / 2 - Não]");
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

    public void imprimirCurso(){
        Scanner scanner = new Scanner(System.in);
        DisciplinaXCursoRepository disciplinaXCursoRepository = new DisciplinaXCursoRepository();
        DisciplinaRepository disciplinaRepository = new DisciplinaRepository();
        int escolhaCurso = 0;

        try {
            System.out.println("Escolha o curso:");
            List<Curso> cursos = listarCurso();
            escolhaCurso = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.println(cursos.get(escolhaCurso));

            List<Disciplina> disciplinas = disciplinaRepository.listarPorId(disciplinaXCursoRepository.listarPorCurso(cursos.get(escolhaCurso).getIdCurso()));
            System.out.println("Disciplinas: ");
            for (int i = 0; i < disciplinas.size(); i++) {
                System.out.println(disciplinas.get(i));
            }

        } catch (SQLException e) {
            e.getCause();
        }


    }
}
