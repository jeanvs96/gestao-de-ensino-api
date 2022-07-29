package br.com.dbccompany.time7.gestaodeensino.dto.usuario;

import br.com.dbccompany.time7.gestaodeensino.enums.AtivarDesativarUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UsuarioAtivarDesativarDTO {

    @NotNull
    @Schema(description = "ID do usuário que deseja desativar")
    private Integer idUsuario;

    @NotNull
    @Schema(description = "Status do usuário true / false", example = "true")
    private AtivarDesativarUsuario status;
}
