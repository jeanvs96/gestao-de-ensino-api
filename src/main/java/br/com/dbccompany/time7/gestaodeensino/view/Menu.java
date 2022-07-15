package br.com.dbccompany.time7.gestaodeensino.view;



import br.com.dbccompany.time7.gestaodeensino.entity.Aluno;
import br.com.dbccompany.time7.gestaodeensino.entity.Colaborador;
import br.com.dbccompany.time7.gestaodeensino.service.*;
import br.com.dbccompany.time7.gestaodeensino.service.factory.PessoaEnderecoFactory;

import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    Integer opcao;
    Boolean controle = true;

    AlunoService alunoService = new AlunoService();

    EnderecoService enderecoService = new EnderecoService();
    ProfessorService professorService = new ProfessorService();
    DisciplinaService disciplinaService = new DisciplinaService();
    CursoService cursoService = new CursoService();
    NotaService notaService = new NotaService();



    public void menuPrincipal(){
        while (controle) {
            System.out.println("----------------------------");
            System.out.println("##SEJA BEM VINDO A ESCOLA##");
            System.out.println("----------------------------");
            System.out.println("Digite 1 para acessar Portal do Aluno");
            System.out.println("Digite 2 para acessar Portal do Professor");
            System.out.println("Digite 3 para acessar Portal de Gestão");
            System.out.println("Digite 0 para sair");
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 0 -> {
                    controle = false;
                    break;
                }
                case 1 -> {
                    menuPortalDoAluno();
                }
                case 2 -> {
                    menuAreaDoProfessor();
                }
                case 3 -> {
                    menuAreaDeGestao();
                }
                default -> {
                    System.out.println("Opção inválida");
                }
            }
        }
    }
    public void menuPortalDoAluno() {
        System.out.println("----------------------------");
        System.out.println("    ##PORTAL DO ALUNO##     ");
        System.out.println("----------------------------");
        System.out.println("Digite 1 para conferir as notas:");
        System.out.println("Digite 2 para voltar");
        System.out.println("Digite 0 para sair");
        opcao = Integer.parseInt(scanner.nextLine());
        switch (opcao) {
            case 0 -> {
                controle = false;
                break;
            }
            case 1 -> {
                notaService.imprimirNotas();
                System.out.println("---------------------------");
                menuPortalDoAluno();

            }
            case 2 -> {
                menuPrincipal();

            }
            default -> {
                menuPortalDoAluno();
            }
        }

    }

    public void menuAreaDoProfessor() {
//        limpaTela();
        System.out.println("----------------------------");
        System.out.println("   ##PORTAL DO PROFESSOR##  ");
        System.out.println("----------------------------");
        System.out.println("Digite 1 para adicionar ou editar notas de alunos:");
        System.out.println("Digite 2 para voltar para o Menu Principal");
        System.out.println("Digite 0 para sair");
        opcao = Integer.parseInt(scanner.nextLine());
        switch (opcao) {
            case 0 -> {
                controle = false;
                break;
            }
            case 1 -> {
                notaService.atualizarNotasAluno();
                System.out.println("---------------------------");
                menuAreaDoProfessor();

            }
            case 2 -> {
                menuPrincipal();
            }
            default -> {
                menuAreaDoProfessor();
            }
        }
    }

    public void menuAreaDeGestao() {
//        limpaTela();
        System.out.println("----------------------------");
        System.out.println("    ##PORTAL DO GESTOR##     ");
        System.out.println("----------------------------");
        System.out.println("Digite 1 para gerenciar alunos");
        System.out.println("Digite 2 para gerenciar colaboradores");
        System.out.println("Digite 3 para gerenciar curso");
        System.out.println("Digite 4 para gerenciar disciplina");
        System.out.println("Digite 5 para voltar ao Menu Principal");
        System.out.println("Digite 0 para sair");
        opcao = Integer.parseInt(scanner.nextLine());
        switch (opcao) {
            case 0 -> {
                controle = false;
                break;
            }
            case 1 -> {
                menuGerenciarAlunos();
                System.out.println("---------------------------");
                menuAreaDeGestao();
            }
            case 2 -> {
                menuGerenciarColaboradores();
                System.out.println("---------------------------");
                menuAreaDeGestao();
            }
            case 3 -> {
                menuGerenciarCurso();
                System.out.println("---------------------------");
                menuAreaDeGestao();
            }
            case 4 -> {
                menuGerenciarDisciplina();
                System.out.println("---------------------------");
                menuAreaDeGestao();
            }
            case 5 -> {
                menuPrincipal();
            }
            default -> {
                menuAreaDeGestao();
            }
        }
    }

    public void menuGerenciarAlunos() {
        System.out.println("Digite 1 para adicionar um aluno");
        System.out.println("Digite 2 para atualizar as informações de um aluno");
        System.out.println("Digite 3 para remover um aluno");
        System.out.println("Digite 4 para acessar informações de um aluno");
        System.out.println("Digite 5 para voltar ao Menu Principal");
        System.out.println("Digite 0 para sair");
        opcao = Integer.parseInt(scanner.nextLine());
        switch (opcao) {
            case 0 -> {
                controle = false;
                break;
            }
            case 1 -> {
                Aluno aluno = PessoaEnderecoFactory.criarAluno();
                enderecoService.adicionarEndereco(aluno.getEndereco());
                alunoService.adicionarAluno(aluno);
                System.out.println("---------------------------");
                menuGerenciarAlunos();
            }
            case 2 -> {
                alunoService.editarAluno();
                System.out.println("---------------------------");
                menuGerenciarAlunos();
            }
            case 3 -> {
                alunoService.removerAluno();
                System.out.println("---------------------------");
                menuGerenciarAlunos();
            }
            case 4 -> {
                alunoService.imprimirInformacoesDoAluno();
                System.out.println("---------------------------");
                menuGerenciarAlunos();
            }
            case 5 -> {
                menuPrincipal();
            }
            default -> {
                menuGerenciarAlunos();
            }
        }
    }

    public void menuGerenciarColaboradores() {
//        limpaTela();
        System.out.println("Digite 1 para adicionar um colaborador");
        System.out.println("Digite 2 para atualizar as informações de um colaborador");
        System.out.println("Digite 3 para remover um colaborador");
        System.out.println("Digite 4 para acessar informações de um colaborador");
        System.out.println("Digite 5 para voltar ao Menu Principal");
        System.out.println("Digite 0 para sair");
        opcao = Integer.parseInt(scanner.nextLine());
        switch (opcao) {
            case 0 -> {
                controle = false;
                break;
            }
            case 1 -> {
                Colaborador colaborador = PessoaEnderecoFactory.criarColaborador();
                enderecoService.adicionarEndereco(colaborador.getEndereco());
                professorService.adicionarProfessor(colaborador);

                System.out.println("---------------------------");
                menuGerenciarColaboradores();
            }
            case 2 -> {
                professorService.editarProfessor();
                System.out.println("---------------------------");
                menuGerenciarColaboradores();
            }
            case 3 -> {
                professorService.removerProfessor();
                System.out.println("---------------------------");
                menuGerenciarColaboradores();
            }
            case 4 -> {
                professorService.imprimirInformacoesProfessor();
                System.out.println("---------------------------");
                menuGerenciarColaboradores();
            }
            case 5 -> {
                menuPrincipal();
            }
        }
    }

    public void menuGerenciarCurso() {
        System.out.println("Digite 1 para adicionar um curso");
        System.out.println("Digite 2 para atualizar as informações de um curso");
        System.out.println("Digite 3 para remover um curso");
        System.out.println("Digite 4 para acessar informações de um curso");
        System.out.println("Digite 5 para adicionar uma disciplina à um curso");
        System.out.println("Digite 6 para remover uma disciplina de um curso");
        System.out.println("Digite 7 para voltar ao Menu Principal");
        System.out.println("Digite 0 para sair");
        opcao = Integer.parseInt(scanner.nextLine());
        switch (opcao) {
            case 0 -> {
                controle = false;
                break;
            }
            case 1 -> {
                cursoService.adicionarCurso();
                System.out.println("---------------------------");
                menuGerenciarCurso();
            }
            case 2 -> {
                cursoService.atualizarCurso();
                System.out.println("---------------------------");
                menuGerenciarCurso();
            }
            case 3 -> {
                cursoService.removerCurso();
                System.out.println("---------------------------");
                menuGerenciarCurso();
            }
            case 4 -> {
                cursoService.imprimirCurso();
                System.out.println("---------------------------");
                menuGerenciarCurso();
            }
            case 5 -> {
                cursoService.adicionarDisciplinaNoCurso();
                System.out.println("---------------------------");
                menuGerenciarCurso();
            }
            case 6 -> {
                cursoService.removerDisciplinaDoCurso();
                System.out.println("---------------------------");
                menuGerenciarCurso();
            }
            case 7 -> {
                menuPrincipal();
            }
            default -> {
                menuGerenciarCurso();
            }
        }


    }

    public void menuGerenciarDisciplina() {
        System.out.println("Digite 1 para adicionar uma disciplina");
        System.out.println("Digite 2 para atualizar as informações de uma disciplina");
        System.out.println("Digite 3 para remover uma disciplina");
        System.out.println("Digite 4 para acessar informações de uma disciplina");
        System.out.println("Digite 5 para voltar ao Menu Principal");
        System.out.println("Digite 0 para sair");
        opcao = Integer.parseInt(scanner.nextLine());
        switch (opcao) {
            case 0 -> {
                controle = false;
                break;
            }
            case 1 -> {
                disciplinaService.adicionarDisciplina();
                System.out.println("---------------------------");
                menuGerenciarDisciplina();
            }
            case 2 -> {
                disciplinaService.atualizarDisciplina();
                System.out.println("---------------------------");
                menuGerenciarDisciplina();
            }
            case 3 -> {
                disciplinaService.removerDisciplina();
                System.out.println("---------------------------");
                menuGerenciarDisciplina();
            }
            case 4 -> {
                disciplinaService.imprimirInformacoesDisciplina();
                System.out.println("---------------------------");
                menuGerenciarDisciplina();
            }
            case 5 -> {
                menuPrincipal();
            }
            default -> {
                System.out.println("Informe uma opção válida.");
            }
        }
    }
}

