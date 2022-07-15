package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoDTO;
import br.com.dbccompany.time7.gestaodeensino.service.EnderecoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/endereco")
@Validated
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping("${idPessoa}")
    public ResponseEntity<EnderecoDTO> listByIdPessoa(@PathVariable Integer idPessoa) {
        return new ResponseEntity<>(enderecoService.listByIdPessoa(idPessoa), HttpStatus.OK);
    }
}