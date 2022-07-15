package br.com.dbccompany.time7.gestaodeensino.service;

import exceptions.NotaException;
import models.Aluno;
import models.Disciplina;
import models.DisciplinaXCurso;
import models.Nota;
import repository.DisciplinaRepository;
import repository.DisciplinaXCursoRepository;
import repository.NotaRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NotaService {

    NotaRepository notaRepository;

    public NotaService() {
        notaRepository = new NotaRepository();
    }

    public void adicionerNotasAluno(Integer idCurso, Integer idAluno) {
        DisciplinaXCursoRepository disciplinaXCursoRepository = new DisciplinaXCursoRepository();

        try {
            List<DisciplinaXCurso> listaDiciplinaXCurso = disciplinaXCursoRepository.listarPorCurso(idCurso);

            for (DisciplinaXCurso itemDisciplinaXCurso : listaDiciplinaXCurso) {
                Nota nota = new Nota();
                nota.setIdDisciplina(itemDisciplinaXCurso.getIdDisciplina());
                nota.setIdAluno(idAluno);
                nota.setNota1(0.0);
                nota.setNota2(0.0);
                nota.setNota3(0.0);
                nota.setNota4(0.0);
                nota.setMedia(0.0);
                notaRepository.adicionerNotasAluno(nota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void atualizarNotasAluno() {
        Scanner scanner = new Scanner(System.in);
        AlunoService alunoService = new AlunoService();
        Integer escolhaAluno;
        Integer escolhaDisciplina;
        Integer idAluno;
        Integer idCursoEscolhaAluno;
        Integer idDisciplina;
        DisciplinaXCursoRepository disciplinaXCursoRepository = new DisciplinaXCursoRepository();

        System.out.println("Deseja alterar notas de qual aluno? ");
        List<Aluno> alunos = alunoService.listarAlunos();
        escolhaAluno = Integer.parseInt(scanner.nextLine());
        idAluno = alunos.get(escolhaAluno - 1).getIdAluno();
        idCursoEscolhaAluno = alunos.get(escolhaAluno - 1).getIdCurso();

        try {
            System.out.println("Escolha a disciplina: ");
            DisciplinaRepository disciplinaRepository = new DisciplinaRepository();
            List<Disciplina> listaDisciplina = disciplinaRepository.listarPorId(disciplinaXCursoRepository.listarPorCurso(idCursoEscolhaAluno));
            for (int i = 0; i < listaDisciplina.size(); i++) {
                System.out.println((i + 1) + " - " + listaDisciplina.get(i).getNome());
            }
            escolhaDisciplina = Integer.parseInt(scanner.nextLine());
            idDisciplina = listaDisciplina.get(escolhaDisciplina - 1).getIdDisciplina();
            Nota nota = notaRepository.listarPorDisciplina(idDisciplina, idAluno);
            System.out.println("Qual nota deseja adicionar: [1 - N1 | 2 - N2 | 3 - N3 | 4 - N4]");
            int opcao = Integer.parseInt(scanner.nextLine());
            try{switch (opcao) {
                case 1 -> {
                    System.out.println("Informe a Nota 1: ");
                    nota.setNota1(Double.parseDouble(scanner.nextLine()));
                    if (nota.getNota1()<0){
                        throw new NotaException("O valor da nota deve ser um número positivo");
                    }
                }
                case 2 -> {
                    System.out.println("Informe a Nota 2: ");
                    nota.setNota2(Double.parseDouble(scanner.nextLine()));
                    if (nota.getNota2()<0){
                        throw new NotaException("O valor da nota deve ser um número positivo");
                    }
                }
                case 3 -> {
                    System.out.println("Informe a Nota 3: ");
                    nota.setNota3(Double.parseDouble(scanner.nextLine()));
                    if (nota.getNota3()<0){
                        throw new NotaException("O valor da nota deve ser um número positivo");
                    }
                }
                case 4 -> {
                    System.out.println("Informe a Nota 4: ");
                    nota.setNota4(Double.parseDouble(scanner.nextLine()));
                    if (nota.getNota4()<0){
                        throw new NotaException("O valor da nota deve ser um número positivo");
                    }
                }
                default -> {
                    System.out.println("Informe uma opção válida.");
                }
            }}catch(NotaException e){
                e.getMessage();
                e.printStackTrace();
            }
            List<Double> notas = new ArrayList<>();
            if (nota.getNota1() != null && nota.getNota1() != 0) {
                notas.add(nota.getNota1());
            }
            if (nota.getNota2() != null && nota.getNota2() != 0) {
                notas.add(nota.getNota2());
            }
            if (nota.getNota3() != null && nota.getNota3() != 0) {
                notas.add(nota.getNota3());
            }
            if (nota.getNota4() != null && nota.getNota4() != 0) {
                notas.add(nota.getNota4());
            }
            double media = 0;
            for (int i = 0; i < notas.size(); i++) {
                media += notas.get(i);
            }
            nota.setMedia(media/notas.size());
            notaRepository.atualizarNotasDisciplina(nota.getIdNota(), nota);

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void imprimirNotas(){
        Scanner scanner = new Scanner(System.in);
        AlunoService alunoService = new AlunoService();
        Integer escolhaAluno;
        Integer idAluno;
        Integer idCursoEscolhaAluno;
        Integer idDisciplina;
        DisciplinaXCursoRepository disciplinaXCursoRepository = new DisciplinaXCursoRepository();
        DisciplinaRepository disciplinaRepository = new DisciplinaRepository();

        try {
            System.out.println("Escolha o aluno: ");
            List<Aluno> alunos = alunoService.listarAlunos();
            escolhaAluno = Integer.parseInt(scanner.nextLine());
            idAluno = alunos.get(escolhaAluno - 1).getIdAluno();
            idCursoEscolhaAluno = alunos.get(escolhaAluno - 1).getIdCurso();
            List<Disciplina> listaDisciplina = disciplinaRepository.listarPorId(disciplinaXCursoRepository.listarPorCurso(idCursoEscolhaAluno));
            for (int i = 0; i < listaDisciplina.size(); i++) {
                System.out.println((i + 1) + " - " + listaDisciplina.get(i).getNome());
                idDisciplina = listaDisciplina.get(i).getIdDisciplina();
                Nota nota = notaRepository.listarPorDisciplina(idDisciplina, idAluno);
                System.out.println("N1: " + nota.getNota1() + "| N2: " + nota.getNota2() + "| N3: " + nota.getNota3() + "| N4: " + nota.getNota4() );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
