package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoUpdateDTO;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.response.Response;
import br.com.dbccompany.time7.gestaodeensino.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Response
    @GetMapping("$/{idEndereco}")
    public ResponseEntity<EnderecoDTO> getByIdPessoa(@PathVariable Integer idEndereco) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.getById(idEndereco), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar endereço", description = "Adiciona um endereço, vinculando-o à uma pessoa existente")
    @Response
    @PostMapping()
    public ResponseEntity<EnderecoDTO> post(@Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.postEndereco(enderecoCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar endereço", description = "Atualiza um endereço, localizando-o por seu ID")
    @Response
    @PutMapping("$/{idEndereco}")
    public ResponseEntity<EnderecoDTO> put(@PathVariable Integer idEndereco, @Valid @RequestBody EnderecoUpdateDTO enderecoUpdateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(enderecoService.putEndereco(idEndereco, enderecoUpdateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Remover endereço", description = "Remove um endereço, localizando-o por seu ID")
    @Response
    @DeleteMapping("$/{idEndereco}")
    public void delete(@PathVariable Integer idEndereco) throws RegraDeNegocioException {
        enderecoService.deleteEndereco(idEndereco);
    }
}