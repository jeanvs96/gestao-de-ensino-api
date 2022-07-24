package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.paginacao.PageDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.professor.ProfessorCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.professor.ProfessorDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.professor.ProfessorUpdateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.relatorios.RelatorioAlunosMaioresNotasDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.relatorios.RelatorioProfessoresMenoresSalariosDTO;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.response.Response;
import br.com.dbccompany.time7.gestaodeensino.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;


    @Operation(summary = "Adicionar professor", description = "Insere professor no banco de dados")
    @Response
    @PostMapping
    public ResponseEntity<ProfessorDTO> save(@RequestBody @Valid ProfessorCreateDTO professorCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(professorService.save(professorCreateDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar professor", description = "Atualiza professor existente no banco de dados")
    @Response
    @PutMapping("/{idProfessor}")
    public ResponseEntity<ProfessorDTO> update(@PathVariable("idProfessor") Integer id, @RequestBody @Valid ProfessorUpdateDTO professorDTOAtualizar) throws RegraDeNegocioException {
        return new ResponseEntity<>(professorService.update(id, professorDTOAtualizar), HttpStatus.OK);
    }

    @Operation(summary = "Deletar professor", description = "Deleta professor existente no banco de dados")
    @Response
    @DeleteMapping("/{idProfessor}")
    public void delete(@PathVariable("idProfessor") Integer id) throws SQLException, RegraDeNegocioException {
        professorService.delete(id);
    }

    @Operation(summary = "Listar professores", description = "Lista todos os professores do banco")
    @Response
    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> list() throws RegraDeNegocioException {
        return new ResponseEntity<>(professorService.list(), HttpStatus.OK);
    }

    @Operation(summary = "Listar professores paginados", description = "Lista todos os professores paginados do banco")
    @Response
    @GetMapping("paginado")
    public PageDTO<ProfessorDTO> paginatedList(Integer pagina, Integer quantidadeDeRegistros) throws RegraDeNegocioException {
        return professorService.paginatedList(pagina, quantidadeDeRegistros);
    }


    @Operation(summary = "Listar professor por ID", description = "Lista o professor do banco com o ID informado")
    @Response
    @GetMapping("/{idProfessor}") // localhost:8080/pessoa/byname?nome=Paulo
    public ResponseEntity<ProfessorDTO> listById(@PathVariable("idProfessor") Integer idProfessor) throws RegraDeNegocioException {
        return new ResponseEntity<>(professorService.listById(idProfessor), HttpStatus.OK);
    }

    @Operation(summary = "Listar professores por nome", description = "Lista todos professores do banco que contem o nome informado")
    @Response
    @GetMapping("/byNome/{nome}")
    public ResponseEntity<List<ProfessorDTO>> listByNome(@PathVariable("nome") String nomeProfessor) throws RegraDeNegocioException {
        return new ResponseEntity<>(professorService.listByName(nomeProfessor), HttpStatus.OK);
    }

    @Response
    @Operation(summary = "Relatório com os professores por ordem decrescente de salários",
            description = "Cria um relatório com os professores ordenados de forma decrescente por salário mensal, com seus respectivos nomes, registro de trabalho, salário e disciplinas ministradas.")
    @GetMapping("/relatorio-maiores-salarios")
    public ResponseEntity<List<RelatorioProfessoresMenoresSalariosDTO>> relatorioProfessorSalario() {
        return ResponseEntity.ok(professorService.relatorioProfessorSalario());
    }

}