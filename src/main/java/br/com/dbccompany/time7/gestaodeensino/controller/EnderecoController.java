package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoDTO;
import br.com.dbccompany.time7.gestaodeensino.service.EnderecoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/endereco")
@Validated
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping("${idPessoa}/pessoa")
    public ResponseEntity<EnderecoDTO> getByIdPessoa(@PathVariable Integer idPessoa) {
        return null;
    }

    @PostMapping()
    public ResponseEntity<EnderecoDTO> post(@RequestBody EnderecoCreateDTO enderecoCreateDTO) {
        return new ResponseEntity<>(enderecoService.postEndereco(enderecoCreateDTO), HttpStatus.OK);
    }

    @PutMapping("${idEndereco}")
    public ResponseEntity<EnderecoDTO> put(@PathVariable Integer idEndereco, @RequestBody EnderecoCreateDTO enderecoCreateDTO) {
        return new ResponseEntity<>(enderecoService.putEndereco(idEndereco, enderecoCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("${idEndereco}")
    public void delete(@PathVariable Integer idEndereco) {
        enderecoService.delete(idEndereco);
    }
}