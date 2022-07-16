package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.ProfessorCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.ProfessorDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Professor;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.service.ProfessorService;
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
@RequestMapping("/professor")
@Validated
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;


    @Operation(summary = "Adicionar professor", description = "Insere professor no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o corpo do professor inserido"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ProfessorDTO> post(@RequestBody @Valid ProfessorCreateDTO professorCreateDTO) {
        return new ResponseEntity<>(professorService.post(professorCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar professor", description = "Atualiza professor existente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o corpo do professor atualizado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idProfessor}")
    public ResponseEntity<ProfessorDTO> put(@PathVariable("idProfessor") Integer id, @RequestBody @Valid ProfessorCreateDTO professorDTOAtualizar) throws SQLException, RegraDeNegocioException {
        return new ResponseEntity<>(professorService.put(id, professorDTOAtualizar), HttpStatus.OK);
    }

    @Operation(summary = "Deletar professor", description = "Deleta professor existente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Status OK"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idProfessor}")
    public void delete(@PathVariable("idProfessor") Integer id) throws SQLException, RegraDeNegocioException {
        professorService.delete(id);
    }

    @Operation(summary = "Listar professores", description = "Lista todos os professores do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de professores"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(professorService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar professor por ID", description = "Lista o professor do banco com o ID informado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o professor pelo parâmetro ID"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idProfessor}") // localhost:8080/pessoa/byname?nome=Paulo
    public ResponseEntity<ProfessorDTO> listById(@PathVariable("idProfessor") Integer idProfessor) throws RegraDeNegocioException, SQLException {
        return new ResponseEntity<>(professorService.listById(idProfessor), HttpStatus.OK);
    }


}