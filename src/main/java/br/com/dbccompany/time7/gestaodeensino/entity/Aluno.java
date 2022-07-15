package br.com.dbccompany.time7.gestaodeensino.entity;

public class Aluno extends Pessoa{
    private Integer idAluno, matricula, idCurso, idEndereco;


    public Aluno() {}
    public Aluno(String nome) {
        super(nome);
    }

    public Aluno(String nome, String telefone, String email, Endereco endereco, Curso curso) {
        super(nome, telefone, email, endereco);
    }

    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public Integer getMatricula() {
        return this.matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }


    @Override
    public String toString() {
        return "Nome: " + this.getNome() +
                "\nMatrícula: " + this.matricula;
    }
}
