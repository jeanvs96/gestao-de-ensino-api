package br.com.dbccompany.time7.gestaodeensino.service.factory;

import models.Curso;
import models.Disciplina;
import service.ProfessorService;

import java.util.Scanner;

public class CursoDisciplinaFactory {
    static Scanner scanner = new Scanner(System.in);

    public static Curso criarCurso() {
        System.out.println("Informe o nome do novo curso:");
        String nomeDoCurso = scanner.nextLine();
        Curso novoCurso = new Curso();
        novoCurso.setNome(nomeDoCurso);

        return novoCurso;
    }

    public static Disciplina criarDisciplina() {
        Integer opcao = 0;
        Integer escolhaProfessor = 0;
        System.out.println("Digite o nome da nova Disciplina: ");
        String nomeDaDisciplina = scanner.nextLine();
        Disciplina disciplinaNova = new Disciplina();
        disciplinaNova.setNome(nomeDaDisciplina);
        System.out.println("Deseja atribuir um professor a disciplina? [1 - Sim / 2 - Não]");
        opcao = Integer.parseInt(scanner.nextLine());
        if (opcao == 1) {
            System.out.println("Informe o numero referente ao professor: ");
            ProfessorService professorService = new ProfessorService();
            professorService.listarProfessores();
            escolhaProfessor = Integer.parseInt(scanner.nextLine());
            Integer professorEscolhido = professorService.listarProfessores().get(escolhaProfessor - 1).getIdColaborador();
            disciplinaNova.setIdProfessor(professorEscolhido);
        } else {
            disciplinaNova.setIdProfessor(null);
        }

        return disciplinaNova;
    }
}
