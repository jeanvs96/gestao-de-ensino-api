package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.AlunoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.AlunoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.AlunoUpdateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.PageDTO;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.response.Response;
import br.com.dbccompany.time7.gestaodeensino.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/aluno")
@AllArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;


    @Operation(summary = "Adicionar aluno", description = "Insere aluno no banco de dados")
    @Response
    @PostMapping
    public ResponseEntity<AlunoDTO> save(@Valid @RequestBody AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(alunoService.save(alunoCreateDTO));}

    @Operation(summary = "Atualizar aluno", description = "Atualiza aluno existente no banco de dados")
    @Response
    @PutMapping("{idAluno}")
    public ResponseEntity<AlunoDTO> update(@PathVariable("idAluno") Integer idAluno,
                                           @Valid@RequestBody AlunoUpdateDTO alunoAtualizar) throws RegraDeNegocioException {
        return ResponseEntity.ok(alunoService.update(idAluno, alunoAtualizar));
    }

    @Operation(summary = "Deletar aluno", description = "Deleta aluno existente no banco de dados")
    @Response
    @DeleteMapping("/{idAluno}")
    public void delete(@PathVariable("idAluno") Integer id) throws RegraDeNegocioException {
        alunoService.delete(id);
    }

    @Operation(summary = "Listar alunos", description = "Lista todos os alunos do banco")
    @Response
    @GetMapping
    public List<AlunoDTO> list() throws RegraDeNegocioException {
        return alunoService.list();
    }

    @Operation(summary = "Listar alunos paginados", description = "Lista todos os alunos paginados do banco")
    @Response
    @GetMapping("paginado")
    public PageDTO<AlunoDTO> paginatedList(Integer pagina, Integer quantidadeDeRegistros) throws RegraDeNegocioException {
        return alunoService.paginatedList(pagina, quantidadeDeRegistros);
    }

    @Operation(summary = "Listar aluno por ID", description = "Lista o aluno do banco com o ID informado")
    @Response
    @GetMapping("/{idAluno}")  //localhost:8080/aluno/1
    public AlunoDTO listById(@PathVariable("idAluno") Integer id) throws RegraDeNegocioException {
        return alunoService.listById(id);
    }

}