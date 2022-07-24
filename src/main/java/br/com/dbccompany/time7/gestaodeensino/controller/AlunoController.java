package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.aluno.AlunoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.aluno.AlunoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.aluno.AlunoUpdateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.paginacao.PageDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.relatorios.RelatorioAlunosMaioresNotasDTO;
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


    @PostMapping
    @Response
    @Operation(summary = "Adicionar aluno", description = "Insere aluno no banco de dados")
    public ResponseEntity<AlunoDTO> save(@Valid @RequestBody AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(alunoService.save(alunoCreateDTO));}

    @PutMapping("{idAluno}")
    @Response
    @Operation(summary = "Atualizar aluno", description = "Atualiza aluno existente no banco de dados")
    public ResponseEntity<AlunoDTO> update(@PathVariable("idAluno") Integer idAluno,
                                           @Valid@RequestBody AlunoUpdateDTO alunoAtualizar) throws RegraDeNegocioException {
        return ResponseEntity.ok(alunoService.update(idAluno, alunoAtualizar));
    }

    @DeleteMapping("/{idAluno}")
    @Response
    @Operation(summary = "Deletar aluno", description = "Deleta aluno existente no banco de dados")
    public void delete(@PathVariable("idAluno") Integer id) throws RegraDeNegocioException {
        alunoService.delete(id);
    }

    @GetMapping
    @Response
    @Operation(summary = "Listar alunos", description = "Lista todos os alunos do banco")
    public ResponseEntity<List<AlunoDTO>> list() throws RegraDeNegocioException {
        return ResponseEntity.ok(alunoService.list());
    }

    @GetMapping("paginado")
    @Response
    @Operation(summary = "Listar alunos paginados", description = "Lista todos os alunos paginados do banco")
    public ResponseEntity<PageDTO<AlunoDTO>> paginatedList(Integer pagina, Integer quantidadeDeRegistros) {
        return ResponseEntity.ok(alunoService.paginatedList(pagina, quantidadeDeRegistros));
    }

    @GetMapping("/{idAluno}")  //localhost:8080/aluno/1
    @Response
    @Operation(summary = "Listar aluno por ID", description = "Lista o aluno do banco com o ID informado")
    public ResponseEntity<AlunoDTO> listById(@PathVariable("idAluno") Integer id) throws RegraDeNegocioException {
        return ResponseEntity.ok(alunoService.listById(id));
    }

    @GetMapping("/relatorio-maiores-notas")
    @Response
    @Operation(summary = "Relatório de alunos por ordem de notas",
            description = "Cria um relatório com os alunos ordenados por nota, com seus respectivos nome, curso, matricula e média.")
    public ResponseEntity<List<RelatorioAlunosMaioresNotasDTO>> relatorioMaioresNotas() {
        return ResponseEntity.ok(alunoService.relatorioAlunoNota());
    }
}