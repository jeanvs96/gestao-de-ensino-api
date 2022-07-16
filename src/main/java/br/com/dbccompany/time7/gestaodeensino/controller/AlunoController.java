package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.AlunoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.AlunoDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Aluno;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/aluno")
public class AlunoController {
    @Autowired
    private AlunoService alunoService;

    @Operation(summary = "Listar alunos", description = "Lista todos os alunos do banco")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a lista de alunos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public List<AlunoDTO> list() {
        return alunoService.listarAlunos();
    }

    @Operation(summary = "Listar aluno por ID", description = "Lista o aluno do banco com o ID informado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna o aluno correspondente ao ID informado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idAluno}")  //localhost:8080/aluno/1
    public Aluno listById(@PathVariable("idAluno") Integer id) throws Exception {
        return alunoService.getAlunoById(id);
    }

    @Operation(summary = "Adicionar aluno", description = "Insere aluno no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Aluno adicionado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<AlunoDTO> create (@Valid @RequestBody AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(alunoService.post(alunoCreateDTO));}

    @Operation(summary = "Atualizar aluno", description = "Atualiza aluno existente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Dados do aluno atualizados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("{idAluno}")
    public ResponseEntity<AlunoDTO> update(@PathVariable("idAluno") Integer id,
                                             @Valid@RequestBody AlunoCreateDTO alunoAtualizar) throws Exception {
        return ResponseEntity.ok(alunoService.put(id, alunoAtualizar));
    }

    @Operation(summary = "Deletar aluno", description = "Deleta aluno existente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Aluno excluído com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idAluno}")
    public void delete (@PathVariable("idAluno") Integer id) throws Exception {
        alunoService.removerAluno(id);
    }
}