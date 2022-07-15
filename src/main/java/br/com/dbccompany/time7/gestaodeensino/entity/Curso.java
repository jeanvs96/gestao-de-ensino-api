package br.com.dbccompany.time7.gestaodeensino.entity;

public class Curso {
    private String nome;
    private Integer IdCurso;

    public Curso() {}

    public Curso(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdCurso() {
        return IdCurso;
    }

    public void setIdCurso(Integer idCurso) {
        IdCurso = idCurso;
    }

    @Override
    public String toString() {
        return "Curso: " + nome;
    }
}
