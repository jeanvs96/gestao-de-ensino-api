package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.CursoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.CursoDTO;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.response.Response;
import br.com.dbccompany.time7.gestaodeensino.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/curso")
@Validated
@AllArgsConstructor
public class CursoController {

    private final CursoService cursoService;


    @Operation(summary = "Adicionar curso", description = "Insere curso no banco de dados")
    @Response
    @PostMapping
    public ResponseEntity<CursoDTO> save(@RequestBody @Valid CursoCreateDTO cursoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(cursoService.save(cursoCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar curso", description = "Atualiza curso existente no banco de dados")
    @Response
    @PutMapping("/{idCurso}")
    public ResponseEntity<CursoDTO> update(@PathVariable("idCurso") Integer id, @RequestBody @Valid CursoCreateDTO cursoDTOAtualizar) throws RegraDeNegocioException {
        return new ResponseEntity<>(cursoService.update(id, cursoDTOAtualizar), HttpStatus.OK);
    }

    @Operation(summary = "Deletar curso", description = "Deleta curso existente no banco de dados")
    @Response
    @DeleteMapping("/{idCurso}")
    public void delete(@PathVariable("idCurso") Integer id) throws RegraDeNegocioException {
        cursoService.delete(id);
    }

    @Operation(summary = "Listar cursos", description = "Lista todos os cursos do banco")
    @Response
    @GetMapping
    public ResponseEntity<List<CursoDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(cursoService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar disciplina ao curso", description = "Insere uma disciplina ao curso no banco de dados")
    @Response
    @PostMapping("/curso/{idCurso}/disciplina/{idDisciplina}")
    public ResponseEntity<CursoDTO> saveDisciplinaNoCurso(@PathVariable("idCurso") Integer idCurso, @PathVariable("idDisciplina") Integer idDisciplina) {
        return new ResponseEntity<>(cursoService.saveDisciplinaNoCurso(idCurso, idDisciplina), HttpStatus.OK);
    }

    @Operation(summary = "Deletar disciplina do curso", description = "Deleta disciplina do curso no banco de dados")
    @Response
    @DeleteMapping("/curso/{idCurso}/disciplina/{idDisciplina}")
    public void deleteDisciplinaDoCurso(@PathVariable("idCurso") Integer idCurso, @PathVariable("idDisciplina") Integer idDisciplina) throws RegraDeNegocioException {
        cursoService.deleteDisciplinaDoCurso(idCurso, idDisciplina);
    }
}