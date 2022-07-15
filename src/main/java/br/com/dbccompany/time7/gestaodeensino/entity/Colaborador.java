package br.com.dbccompany.time7.gestaodeensino.entity;

public class Colaborador extends Pessoa{
    private Integer idColaborador, registroTrabalho, idEndereco;
    private Double salario;
    private String cargo;

    public Colaborador(String nome) {
        super(nome);
    }

    public Colaborador(String nome, String telefone, String email, Endereco endereco, Double salario, String cargo) {
        super(nome, telefone, email, endereco);
        this.salario = salario;
        this.cargo = cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getRegistroTrabalho() {
        return registroTrabalho;
    }

    public void setRegistroTrabalho(Integer registroTrabalho) {
        this.registroTrabalho = registroTrabalho;
    }

    @Override
    public String toString() {
        return "Nome: " + this.getNome() +
                "\nCargo: " + this.getCargo() +
                "\nRegistro: " + this.getRegistroTrabalho() +
                "\nSalário: R$" + this.getSalario();
    }

    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }


}
