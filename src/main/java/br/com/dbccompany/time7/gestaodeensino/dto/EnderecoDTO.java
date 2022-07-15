package br.com.dbccompany.time7.gestaodeensino.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnderecoDTO extends EnderecoCreateDTO{

    @Schema(description = "ID exclusivo do endereço")
    private Integer idEndereco;
}
