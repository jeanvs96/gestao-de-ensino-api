package br.com.dbccompany.time7.gestaodeensino.entity;

public class Disciplina {
    private Integer idDisciplina;
    private String nome;
    private Integer idProfessor;

    public Disciplina(){}

    public Disciplina(String nome, Integer idProfessor) {
        this.nome = nome;
        this.idProfessor = idProfessor;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void imprimirDisciplina() {

    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }

    @Override
    public String toString() {
        return "Disciplina:  " + this.nome;
    }

}
