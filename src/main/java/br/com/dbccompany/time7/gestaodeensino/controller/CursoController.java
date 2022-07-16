package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.CursoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.CursoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.ProfessorDTO;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/curso")
@Validated
public class CursoController {

    @Autowired
    private CursoService cursoService;


    @Operation(summary = "Adicionar curso", description = "Insere curso no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o corpo do curso inserido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<CursoDTO> post(@RequestBody @Valid CursoCreateDTO cursoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(cursoService.post(cursoCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar curso", description = "Atualiza curso existente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o corpo do curso atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idCurso}")
    public ResponseEntity<ProfessorDTO> put(@PathVariable("idCurso") Integer id, @RequestBody @Valid CursoCreateDTO cursoDTOAtualizar) throws SQLException, RegraDeNegocioException {
        return new ResponseEntity<>(cursoService.put(id, cursoDTOAtualizar), HttpStatus.OK);
    }

    @Operation(summary = "Deletar curso", description = "Deleta curso existente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Status OK"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCurso}")
    public void delete(@PathVariable("idCurso") Integer id) throws SQLException, RegraDeNegocioException {
        cursoService.delete(id);
    }

    @Operation(summary = "Listar cursos", description = "Lista todos os cursos do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de cursos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> list() throws SQLException {
        return new ResponseEntity<>(cursoService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar cursos por nome", description = "Lista todos os cursos do banco com o nome informado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de cursos pelo parâmetro nome"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/byname") // localhost:8080/pessoa/byname?nome=Paulo
    public ResponseEntity<List<ProfessorDTO>> listByName(@RequestParam("nome") String nome) throws RegraDeNegocioException, SQLException {
        return new ResponseEntity<>(cursoService.listByName(nome), HttpStatus.OK);
    }
}