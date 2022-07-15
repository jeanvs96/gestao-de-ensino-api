package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.entity.Disciplina;
import br.com.dbccompany.time7.gestaodeensino.repository.*;
import br.com.dbccompany.time7.gestaodeensino.service.factory.CursoDisciplinaFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DisciplinaService {

    DisciplinaRepository disciplinaRepository;

    public DisciplinaService() {
        disciplinaRepository = new DisciplinaRepository();
    }

    public void adicionarDisciplina() {

        Disciplina disciplina = CursoDisciplinaFactory.criarDisciplina();

        try {
            if (!(this.conferirSeDisciplinaExiste(disciplina))) {
                disciplinaRepository.adicionar(disciplina);
            }
        } catch (SQLException e) {
            e.getCause();
        }
    }

    public Boolean conferirSeDisciplinaExiste(Disciplina disciplina) throws SQLException {
        Boolean controle = false;
        int posicao = 0;
        List<Disciplina> disciplinasDb = disciplinaRepository.listar();
        for (Disciplina db : disciplinasDb) {
            controle = db.getNome().equals(disciplina.getNome());
            if (controle) {
                posicao = db.getIdDisciplina();
            }

        }
        if (posicao != 0) {
            disciplina.setIdDisciplina(posicao);
            return true;
        }
        return false;
    }

    public void atualizarDisciplina() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o numero referente a disciplina que deseja atualizar: ");

        int controleNome = 0;
        int controleProfessor = 0;
        Integer escolhaDisciplina = 0;
        List<Disciplina> disciplinas = listarDisciplina();
        escolhaDisciplina = Integer.parseInt(scanner.nextLine());
        Disciplina disciplinaEscolhida = disciplinas.get(escolhaDisciplina - 1);

        try {
            if (disciplinaRepository.conferirIdDisciplina(disciplinaEscolhida.getIdDisciplina())) {

                System.out.println("Atualizar nome da disciplina? [1 - Sim / 2 - Não]");
                controleNome = Integer.parseInt(scanner.nextLine());
                if (controleNome == 1) {
                    System.out.println("Informe o novo Nome da Disciplina: ");
                    disciplinaEscolhida.setNome(scanner.nextLine());
                } else {
                    System.out.println("Nome atual da disciplina: " + disciplinaEscolhida.getNome());
                }

                System.out.println("Alterar professor da disciplina? [1 - Sim / 2 - Não]");
                controleProfessor = Integer.parseInt(scanner.nextLine());

                if (controleProfessor == 1) {
                    System.out.println("Informe o numero referente ao professor: ");
                    ProfessorService professorService = new ProfessorService();
                    professorService.listarProfessores();
                    Integer escolhaProfessor = Integer.parseInt(scanner.nextLine());
                    Integer professorEscolhido = professorService.listarProfessores().get(escolhaProfessor - 1).getIdColaborador();
                    disciplinaEscolhida.setIdProfessor(professorEscolhido);
                } else {
                    System.out.println("Nome do atual professor da disciplina: " + disciplinaEscolhida.getIdProfessor());
                }

                disciplinaRepository.editar(disciplinaEscolhida.getIdDisciplina(), disciplinaEscolhida);
            }
        } catch (SQLException e) {
            e.getCause();
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

    public void imprimirInformacoesDisciplina() {
        //Em construção
        Integer escolhaDisciplina = 0;
        Scanner scanner = new Scanner(System.in);
        ProfessorRepository professorRepository = new ProfessorRepository();


        try {
            System.out.println("Sobre qual disciplina deseja saber? ");
            List<Disciplina> disciplinas = listarDisciplina();
            escolhaDisciplina = Integer.parseInt(scanner.nextLine());
            Disciplina disciplina = disciplinas.get(escolhaDisciplina - 1);
            System.out.println("\nDisciplina: " + disciplina.getNome());
            System.out.println("Professor: \n" + professorRepository.professorPorId(disciplina.getIdProfessor()));
        } catch (SQLException e) {
            e.getCause();
        }
    }

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
}
