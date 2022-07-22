package br.com.dbccompany.time7.gestaodeensino.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PessoaEntity {

    private String nome;
    private String telefone;
    private String email;
    private Integer idEndereco;
}
