package br.com.dbccompany.time7.gestaodeensino.dto.usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UsuarioLoginDTO {

    @NotNull
    @Schema(example = "user")
    private String login;

    @NotNull
    @Schema(example = "123")
    private String senha;
}
