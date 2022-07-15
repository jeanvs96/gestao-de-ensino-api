package br.com.dbccompany.time7.gestaodeensino.entity;

public class Nota {
    private Integer idNota;
    private Integer idDisciplina;
    private Integer idAluno;
    private Double nota1;
    private Double nota2;
    private Double nota3;
    private Double nota4;
    private Double media;

    public Nota() {
    }

    public Nota(Integer idNota, Integer idDisciplina, Integer idAluno) {
        this.idNota = idNota;
        this.idDisciplina = idDisciplina;
        this.idAluno = idAluno;
    }

    public Integer getIdNota() {
        return idNota;
    }

    public void setIdNota(Integer idNota) {
        this.idNota = idNota;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Double getNota1() {
        return nota1;
    }

    public void setNota1(Double nota1) {
        this.nota1 = nota1;
    }

    public Double getNota2() {
        return nota2;
    }

    public void setNota2(Double nota2) {
        this.nota2 = nota2;
    }

    public Double getNota3() {
        return nota3;
    }

    public void setNota3(Double nota3) {
        this.nota3 = nota3;
    }

    public Double getNota4() {
        return nota4;
    }

    public void setNota4(Double nota4) {
        this.nota4 = nota4;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }


}
