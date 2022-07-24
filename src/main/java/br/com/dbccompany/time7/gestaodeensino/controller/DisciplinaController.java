package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.disciplina.DisciplinaCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.disciplina.DisciplinaDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.disciplina.DisciplinaUpdateDTO;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.response.Response;
import br.com.dbccompany.time7.gestaodeensino.service.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/disciplina")
@Validated
public class DisciplinaController {

    private final DisciplinaService disciplinaService;


    @Operation(summary = "Adicionar disciplina", description = "Adiciona uma disciplina, podendo vincular a mesma à um professor")
    @Response
    @PostMapping()
    public ResponseEntity<DisciplinaDTO> save(@Valid @RequestBody DisciplinaCreateDTO disciplinaCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(disciplinaService.save(disciplinaCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar disciplina", description = "Atualiza uma disciplina, localizando-a por seu ID")
    @Response
    @PutMapping("/{idDisciplina}")
    public ResponseEntity<DisciplinaDTO> update(@PathVariable Integer idDisciplina, @RequestBody DisciplinaUpdateDTO disciplinaUpdateDTO) throws SQLException, RegraDeNegocioException {
        return new ResponseEntity<>(disciplinaService.update(idDisciplina, disciplinaUpdateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Remover disciplina", description = "Remove uma disciplina, localizando-a por seu ID")
    @Response
    @DeleteMapping("/{idDisciplina}")
    public void delete(@PathVariable Integer idDisciplina) throws RegraDeNegocioException {
        disciplinaService.delete(idDisciplina);
    }


    @Operation(summary = "Remove o professor da disciplina", description = "Remove um professor a disciplina")
    @Response
    @PutMapping("/{idDisciplina}/professor/")
    public ResponseEntity<DisciplinaDTO> deleteProfessorDaDisciplina(@PathVariable("idDisciplina") Integer idDisciplina) throws RegraDeNegocioException {
        return new ResponseEntity<>(disciplinaService.deleteProfessorDaDisciplina(idDisciplina), HttpStatus.OK);
    }

    @Operation(summary = "Listar disciplinas", description = "Lista todas as disciplinas")
    @Response
    @GetMapping
    public ResponseEntity<List<DisciplinaDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(disciplinaService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar disciplina por ID", description = "Lista uma disciplina através de seu ID único")
    @Response
    @GetMapping("/{idDisciplina}")
    public ResponseEntity<DisciplinaDTO> getByIdDisciplina(@PathVariable Integer idDisciplina) throws RegraDeNegocioException {
        return new ResponseEntity<>(disciplinaService.listByIdDisciplina(idDisciplina), HttpStatus.OK);
    }
}
