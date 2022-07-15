package br.com.dbccompany.time7.gestaodeensino.service.factory;

import br.com.dbccompany.time7.gestaodeensino.entity.Aluno;
import br.com.dbccompany.time7.gestaodeensino.entity.Professor;
import br.com.dbccompany.time7.gestaodeensino.entity.Endereco;

import java.util.Scanner;

public class PessoaEnderecoFactory {
    static Scanner scanner = new Scanner(System.in);
    public static Aluno criarAluno() {

        System.out.println("Digite o nome completo do Aluno:");
        Aluno aluno = new Aluno(scanner.nextLine());
        System.out.println("Digite o telefone do Aluno:");
        aluno.setTelefone(scanner.nextLine());
        System.out.println("Digite o E-mail do Aluno:");
        aluno.setEmail(scanner.nextLine());
        System.out.println("Digite o endereço do Aluno:");
        aluno.setEndereco(criarEndereco());


        System.out.println("Aluno cadastrado");
        return aluno;
    }
    public static Professor criarColaborador() {
        System.out.println("Digite o nome do professor:");
        Professor professor = new Professor(scanner.nextLine());
        System.out.println("Digite o telefone do Professor:");
        professor.setTelefone(scanner.nextLine());
        System.out.println("Digite o E-mail do Professor:");
        professor.setEmail(scanner.nextLine());
        System.out.println("Digite o cargo do Professor:");
        professor.setCargo(scanner.nextLine());
        System.out.println("Digite o salário do professor:");
        System.out.print("R$");
        professor.setSalario(Double.parseDouble(scanner.nextLine()));
        System.out.println("Digite o endereço do Professor:");
        professor.setEndereco(criarEndereco());
//        for (int i = 0; i < Menu.getListaDeDisciplinas().size(); i++) {
//            System.out.println((i + 1) + " - " + Menu.getListaDeDisciplinas().get(i).getNome());
//        }
//        Integer opcao = Integer.parseInt(scanner.nextLine());
        return professor;
    }

    public static Endereco criarEndereco() {
        System.out.println("Logradouro:");
        Endereco endereco = new Endereco(scanner.nextLine());
        System.out.println("Número:");
        endereco.setNumero(Integer.parseInt(scanner.nextLine()));
        System.out.println("Possui complemento?\n1 - Sim\n2 - Não");
        Integer opcao = Integer.parseInt(scanner.nextLine());
        switch (opcao) {
            case 1 -> {
                System.out.println("Complemento:");
                endereco.setComplemento(scanner.nextLine());
            }
            case 2 -> {
                endereco.setComplemento(null);
            }
        }
        System.out.println("Cidade:");
        endereco.setCidade(scanner.nextLine());
        System.out.println("Estado:");
        endereco.setEstado(scanner.nextLine());
        System.out.println("Cep:");
        endereco.setCep(scanner.nextLine());
        return endereco;
    }
}
