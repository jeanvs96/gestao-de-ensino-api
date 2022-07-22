package br.com.dbccompany.time7.gestaodeensino.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "notas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotaEntity {

    @Id
    @Column(name = "id_notas")
    private Integer idNota;

    @Column(name = "n1")
    private Double nota1;

    @Column(name = "n2")
    private Double nota2;

    @Column(name = "n3")
    private Double nota3;

    @Column(name = "n4")
    private Double nota4;

    @Column(name = "media")
    private Double media;
}
