package br.com.dbccompany.time7.gestaodeensino.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Endereco {

    private String logradouro;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;
    private Integer numero;
    private Integer idEndereco;

}
