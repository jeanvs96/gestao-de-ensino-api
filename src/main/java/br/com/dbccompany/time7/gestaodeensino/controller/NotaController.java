package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.NotaCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.NotaDTO;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.service.NotaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/nota")
@Validated
public class NotaController {

    private final NotaService notaService;

    @GetMapping("/{idAluno}")
    public ResponseEntity<List<NotaDTO>> getByIdAluno(@PathVariable Integer idAluno) throws RegraDeNegocioException {
        return new ResponseEntity<>(notaService.listByIdAluno(idAluno), HttpStatus.OK);
    }

    @PutMapping("/{idNota}")
    public ResponseEntity<NotaDTO> putNota(@PathVariable Integer idNota, @Valid @RequestBody NotaCreateDTO notaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(notaService.atualizarNotasAluno(idNota, notaCreateDTO), HttpStatus.OK);
    }
}
