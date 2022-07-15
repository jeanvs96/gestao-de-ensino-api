package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.entity.Aluno;
import br.com.dbccompany.time7.gestaodeensino.entity.Curso;
import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.EnderecoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.NotaRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AlunoService {
    AlunoRepository alunoRepository;

    public AlunoService() {
        alunoRepository = new AlunoRepository();
    }

    public void adicionarAluno(Aluno aluno) {
        try {
            Scanner scanner = new Scanner(System.in);
            int escolhaCurso;
            int idCursoEscolhido;
            CursoService cursoService = new CursoService();
            NotaService notaService = new NotaService();

            System.out.println("Informe o Curso do aluno: ");
            List <Curso> cursos = cursoService.listarCurso();
            escolhaCurso = Integer.parseInt(scanner.nextLine());
            idCursoEscolhido = cursos.get(escolhaCurso - 1).getIdCurso();
            aluno.setIdCurso(idCursoEscolhido);
            Aluno aluno1 = alunoRepository.adicionar(aluno);

            notaService.adicionerNotasAluno(idCursoEscolhido, aluno1.getIdAluno());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editarAluno() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe o ID do aluno que deseja atualizar dados: ");

        EnderecoRepository enderecoRepository = new EnderecoRepository();
        EnderecoService enderecoService = new EnderecoService();
        int controleNome = 0;
        int controleTelefone = 0;
        int controleEmail = 0;
        int controleEndereco = 0;
        Integer escolhaAluno = 0;
        List<Aluno> alunos = listarAlunos();
        escolhaAluno = Integer.parseInt(scanner.nextLine());
        Aluno alunoEscolhido = alunos.get(escolhaAluno - 1);

        try {
            System.out.println("Atualizar nome do aluno? [1 - Sim / 2 - Não]");
            controleNome = Integer.parseInt(scanner.nextLine());
            if (controleNome == 1) {
                System.out.println("Informe o novo Nome do aluno: ");
                alunoEscolhido.setNome(scanner.nextLine());
            } else {
                System.out.println("Nome atual do aluno: " + alunoEscolhido.getNome());
            }

            System.out.println("Atualizar o telefone do aluno? [1 - Sim / 2 - Não]");
            controleTelefone = Integer.parseInt(scanner.nextLine());
            if (controleTelefone == 1) {
                System.out.println("Informe o novo número de telefone: ");
                alunoEscolhido.setTelefone(scanner.nextLine());
            } else {
                System.out.println("Número de telefone atual: " + alunoEscolhido.getTelefone());
            }

            System.out.println("Atualizar o e-mail do aluno? [1 - Sim / 2 - Não]");
            controleEmail = Integer.parseInt(scanner.nextLine());
            if (controleEmail == 1) {
                System.out.println("Informe o novo e-mail: ");
                alunoEscolhido.setEmail(scanner.nextLine());
            } else {
                System.out.println("E-mail atual: " + alunoEscolhido.getEmail());
            }
            System.out.println("Atualizar endereço? [1 - Sim / 2 - Não]");
            controleEndereco = Integer.parseInt(scanner.nextLine());
            if (controleEndereco == 1) {
                enderecoService.atualizarEndereco(enderecoRepository.pegarEnderecoPorId(alunoEscolhido.getIdEndereco()));
            }
            alunoRepository.editar(alunoEscolhido.getIdAluno(), alunoEscolhido);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerAluno() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual aluno deseja remover?");
        List<Aluno> alunos = this.listarAlunos();
        int opcao = (Integer.parseInt(scanner.nextLine())) - 1;
        try {
            Integer idEndereco = alunos.get(opcao).getIdEndereco();
            NotaRepository notaRepository = new NotaRepository();
            notaRepository.removerNotaPorIdAluno(alunos.get(opcao).getIdAluno());
            alunoRepository.remover(alunos.get(opcao).getIdAluno());
            EnderecoService enderecoService = new EnderecoService();
            enderecoService.removerEndereco(idEndereco);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Aluno> listarAlunos() {
        try {
            List<Aluno> lista = alunoRepository.listar();
            for (int i = 0; i < lista.size(); i++) {
                System.out.println((i + 1) + " - " + lista.get(i).getNome());
            }
            return lista;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void imprimirInformacoesDoAluno() {
        EnderecoRepository enderecoRepository = new EnderecoRepository();
        Scanner scanner = new Scanner(System.in);
        int escolhaAluno = 0;

        try {
            System.out.println("Escolha o aluno:");
            List<Aluno> alunos = listarAlunos();
            escolhaAluno = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.println(alunos.get(escolhaAluno));
            System.out.println(enderecoRepository.pegarEnderecoPorId(alunos.get(escolhaAluno).getIdEndereco()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

