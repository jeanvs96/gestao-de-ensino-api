package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.ProfessorCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.ProfessorDTO;
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
    public ResponseEntity<ProfessorDTO> create(@RequestBody @Valid ProfessorCreateDTO professorCreateDTO) {
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
    public ResponseEntity<ProfessorDTO> update(@PathVariable("idProfessor") Integer id, @RequestBody @Valid ProfessorCreateDTO professorDTOAtualizar) {
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
    public void delete(@PathVariable("idProfessor") Integer id) {
        professorService.delete(id);
    }
}