package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.curso.CursoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.curso.CursoDTO;
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

    @PostMapping
    @Response
    @Operation(summary = "Adicionar curso", description = "Insere curso no banco de dados")
    public ResponseEntity<CursoDTO> save(@RequestBody @Valid CursoCreateDTO cursoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(cursoService.save(cursoCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{idCurso}")
    @Response
    @Operation(summary = "Atualizar curso", description = "Atualiza curso existente no banco de dados")
    public ResponseEntity<CursoDTO> update(@PathVariable("idCurso") Integer id, @RequestBody @Valid CursoCreateDTO cursoDTOAtualizar) throws RegraDeNegocioException {
        return new ResponseEntity<>(cursoService.update(id, cursoDTOAtualizar), HttpStatus.OK);
    }

    @DeleteMapping("/{idCurso}")
    @Response
    @Operation(summary = "Deletar curso", description = "Deleta curso existente no banco de dados")
    public void delete(@PathVariable("idCurso") Integer id) throws RegraDeNegocioException {
        cursoService.delete(id);
    }

    @GetMapping
    @Response
    @Operation(summary = "Listar cursos", description = "Lista todos os cursos do banco")
    public ResponseEntity<List<CursoDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(cursoService.list(), HttpStatus.OK);
    }

    @PostMapping("/curso/{idCurso}/disciplina/{idDisciplina}")
    @Response
    @Operation(summary = "Adicionar disciplina ao curso", description = "Insere uma disciplina ao curso no banco de dados")
    public ResponseEntity<CursoDTO> saveDisciplinaNoCurso(@PathVariable("idCurso") Integer idCurso, @PathVariable("idDisciplina") Integer idDisciplina) throws RegraDeNegocioException {
        return new ResponseEntity<>(cursoService.saveDisciplinaNoCurso(idCurso, idDisciplina), HttpStatus.OK);
    }

    @DeleteMapping("/curso/{idCurso}/disciplina/{idDisciplina}")
    @Response
    @Operation(summary = "Remover disciplina do curso", description = "Deleta disciplina do curso no banco de dados")
    public ResponseEntity<CursoDTO> deleteDisciplinaDoCurso(@PathVariable("idCurso") Integer idCurso, @PathVariable("idDisciplina") Integer idDisciplina) throws RegraDeNegocioException {
        return new ResponseEntity<>(cursoService.deleteDisciplinaDoCurso(idCurso, idDisciplina), HttpStatus.OK);
    }
}