package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoDTO;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@AllArgsConstructor
@RestController
@RequestMapping("/endereco")
@Validated
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Operation(summary = "Listar endereço por ID", description = "Lista um endereço através de seu ID único")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o endereço referenciado pelo ID"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("$/{idEndereco}")
    public ResponseEntity<EnderecoDTO> getByIdPessoa(@PathVariable Integer idEndereco) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.getById(idEndereco), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar endereço", description = "Adiciona um endereço, vinculando-o à uma pessoa existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o corpo do endereço adicionado, com seu novo ID"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping()
    public ResponseEntity<EnderecoDTO> post(@Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.postEndereco(enderecoCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar endereço", description = "Atualiza um endereço, localizando-o por seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o corpo do endereço atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("$/{idEndereco}")
    public ResponseEntity<EnderecoDTO> put(@PathVariable Integer idEndereco, @Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO) throws SQLException, RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.putEndereco(idEndereco, enderecoCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Remover endereço", description = "Remove um endereço, localizando-o por seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna somente o Status Code da requisição HTTP"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("$/{idEndereco}")
    public void delete(@PathVariable Integer idEndereco) throws RegraDeNegocioException {
        enderecoService.deleteEndereco(idEndereco);
    }
}