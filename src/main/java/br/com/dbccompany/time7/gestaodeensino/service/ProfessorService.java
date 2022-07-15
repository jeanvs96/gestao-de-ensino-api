package br.com.dbccompany.time7.gestaodeensino.service;



import br.com.dbccompany.time7.gestaodeensino.entity.Colaborador;
import br.com.dbccompany.time7.gestaodeensino.repository.DisciplinaRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.EnderecoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.ProfessorRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProfessorService {
    ProfessorRepository professorRepository;

    public ProfessorService() {
        professorRepository = new ProfessorRepository();
    }

    public void adicionarProfessor(Colaborador professor) {
        try {
            professorRepository.adicionar(professor);
        } catch (SQLException e) {
            e.getCause();
        }
    }

    public void removerProfessor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual colaborador deseja remover?");
        List<Colaborador> professores = this.listarProfessores();
        int opcao = (Integer.parseInt(scanner.nextLine())) - 1;
        DisciplinaRepository disciplinaRepository = new DisciplinaRepository();
        try {
            Integer idEndereco = professores.get(opcao).getIdEndereco();
            disciplinaRepository.removerProfessor(professores.get(opcao).getIdColaborador());
            professorRepository.remover(professores.get(opcao).getIdColaborador());
            EnderecoService enderecoService = new EnderecoService();
            enderecoService.deleteEndereco(idEndereco);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Colaborador> listarProfessores() {
        try {
            List<Colaborador> colaboradores = professorRepository.listar();
            for (int i = 0; i < colaboradores.size(); i++) {
                System.out.println((i + 1) + " - " + colaboradores.get(i).getNome());
            }
                return colaboradores;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void editarProfessor(){
        Scanner scanner = new Scanner(System.in);
        int controle = 0;
        Integer escolhaProfessor = 0;
        EnderecoRepository enderecoRepository = new EnderecoRepository();
        EnderecoService enderecoService = new EnderecoService();

        try {
            System.out.println("Qual professor deseja atualizar os dados: ");
            List<Colaborador> professores = listarProfessores();
            escolhaProfessor = Integer.parseInt(scanner.nextLine()) - 1;
            Colaborador professorEscolhido = professores.get(escolhaProfessor);

            System.out.println("Atualizar nome do professor? [1 - Sim / 2 - Não]");
            controle = Integer.parseInt(scanner.nextLine());
            if (controle == 1) {
                System.out.println("Nome: ");
                professorEscolhido.setNome(scanner.nextLine());
            }

            System.out.println("Atualizar o telefone do professor? [1 - Sim / 2 - Não]");
            controle = Integer.parseInt(scanner.nextLine());
            if (controle == 1) {
                System.out.println("Telefone: ");
                professorEscolhido.setTelefone(scanner.nextLine());
            }

            System.out.println("Atualizar o e-mail do professor? [1 - Sim / 2 - Não]");
            controle = Integer.parseInt(scanner.nextLine());
            if (controle == 1) {
                System.out.println("E-mail: ");
                professorEscolhido.setEmail(scanner.nextLine());
            }

            System.out.println("Atualizar o salário do professor? [1 - Sim / 2 - Não]");
            controle = Integer.parseInt(scanner.nextLine());
            if (controle == 1) {
                System.out.println("Salário: ");
                System.out.print("R$");
                professorEscolhido.setSalario(Double.parseDouble(scanner.nextLine()));
            }
            this.professorRepository.editar(professorEscolhido.getIdColaborador(), professorEscolhido);

            System.out.println("Atualizar endereço? [1 - Sim / 2 - Não]");
            controle = Integer.parseInt(scanner.nextLine());
            if (controle == 1) {
                enderecoService.putEndereco(enderecoRepository.pegarEnderecoPorId(professorEscolhido.getIdEndereco()));
            }
        } catch (SQLException e) {
            e.getCause();
        }
    }

    public void imprimirInformacoesProfessor() {
        EnderecoRepository enderecoRepository = new EnderecoRepository();
        Scanner scanner = new Scanner(System.in);
        int escolhaProfessor = 0;

        try {
            System.out.println("Escolha o colaborador: ");
            List<Colaborador> professores = this.listarProfessores();
            escolhaProfessor = Integer.parseInt(scanner.nextLine()) - 1;

            System.out.println(professores.get(escolhaProfessor));
            System.out.println(enderecoRepository.pegarEnderecoPorId(professores.get(escolhaProfessor).getIdEndereco()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
