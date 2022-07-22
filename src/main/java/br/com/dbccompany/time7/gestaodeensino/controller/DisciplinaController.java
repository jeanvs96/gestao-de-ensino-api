//package br.com.dbccompany.time7.gestaodeensino.controller;
//
//import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaCreateDTO;
//import br.com.dbccompany.time7.gestaodeensino.dto.DisciplinaDTO;
//import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
//import br.com.dbccompany.time7.gestaodeensino.response.Response;
//import br.com.dbccompany.time7.gestaodeensino.service.DisciplinaService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.sql.SQLException;
//import java.util.List;
//
//@AllArgsConstructor
//@RestController
//@RequestMapping("/disciplina")
//@Validated
//public class DisciplinaController {
//
//    private final DisciplinaService disciplinaService;
//
//    @Operation(summary = "Listar disciplinas", description = "Lista todas as disciplinas")
//    @Response
//    @GetMapping
//    public ResponseEntity<List<DisciplinaDTO>> getDisciplinas() throws RegraDeNegocioException {
//        return new ResponseEntity<>(disciplinaService.getDisciplinas(), HttpStatus.OK);
//    }
//
//    @Operation(summary = "Listar disciplina por ID", description = "Lista uma disciplina através de seu ID único")
//    @Response
//    @GetMapping("/{idDisciplina}")
//    public ResponseEntity<DisciplinaDTO> getByIdDisciplina(@PathVariable Integer idDisciplina) throws RegraDeNegocioException {
//        return new ResponseEntity<>(disciplinaService.getByIdDisciplina(idDisciplina), HttpStatus.OK);
//    }
//
//    @Operation(summary = "Adicionar disciplina", description = "Adiciona uma disciplina, podendo vincular a mesma à um professor")
//    @Response
//    @PostMapping()
//    public ResponseEntity<DisciplinaDTO> postDisciplina(@Valid @RequestBody DisciplinaCreateDTO disciplinaCreateDTO) throws RegraDeNegocioException {
//        return new ResponseEntity<>(disciplinaService.postDisciplina(disciplinaCreateDTO), HttpStatus.OK);
//    }
//
//    @Operation(summary = "Atualizar disciplina", description = "Atualiza uma disciplina, localizando-a por seu ID")
//    @Response
//    @PutMapping("/{idDisciplina}")
//    public ResponseEntity<DisciplinaDTO> putDisciplina(@PathVariable Integer idDisciplina, @RequestBody DisciplinaCreateDTO disciplinaCreateDTO) throws SQLException, RegraDeNegocioException {
//        return new ResponseEntity<>(disciplinaService.putDisciplina(idDisciplina, disciplinaCreateDTO), HttpStatus.OK);
//    }
//
//    @Operation(summary = "Remover disciplina", description = "Remove uma disciplina, localizando-a por seu ID")
//    @Response
//    @DeleteMapping("/{idDisciplina}")
//    public void deleteDisciplina(@PathVariable Integer idDisciplina) throws RegraDeNegocioException {
//        disciplinaService.deleteDisciplina(idDisciplina);
//    }
//}
