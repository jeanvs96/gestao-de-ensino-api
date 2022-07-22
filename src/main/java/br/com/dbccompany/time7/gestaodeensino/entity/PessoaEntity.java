package br.com.dbccompany.time7.gestaodeensino.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class PessoaEntity {

    private String nome;
    private String telefone;
    private String email;
    private Integer idEndereco;
}
