//package br.com.dbccompany.time7.gestaodeensino.controller;
//
//import br.com.dbccompany.time7.gestaodeensino.dto.AlunoCreateDTO;
//import br.com.dbccompany.time7.gestaodeensino.dto.AlunoDTO;
//import br.com.dbccompany.time7.gestaodeensino.dto.AlunoUpdateDTO;
//import br.com.dbccompany.time7.gestaodeensino.entity.AlunoEntity;
//import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
//import br.com.dbccompany.time7.gestaodeensino.response.Response;
//import br.com.dbccompany.time7.gestaodeensino.service.AlunoService;
//import br.com.dbccompany.time7.gestaodeensino.service.EmailService;
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@Validated
//@RequestMapping("/aluno")
//public class AlunoController {
//    @Autowired
//    private AlunoService alunoService;
//    @Autowired
//    private EmailService emailService;
//
//    @Operation(summary = "Listar alunos", description = "Lista todos os alunos do banco")
//    @Response
//    @GetMapping
//    public List<AlunoDTO> list() throws RegraDeNegocioException {
//        return alunoService.listarAlunos();
//    }
//
//    @Operation(summary = "Listar aluno por ID", description = "Lista o aluno do banco com o ID informado")
//    @Response
//    @GetMapping("/{idAluno}")  //localhost:8080/aluno/1
//    public AlunoEntity listById(@PathVariable("idAluno") Integer id) throws RegraDeNegocioException {
//        return alunoService.getAlunoById(id);
//    }
//
//    @Operation(summary = "Adicionar aluno", description = "Insere aluno no banco de dados")
//    @Response
//    @PostMapping
//    public ResponseEntity<AlunoDTO> create (@Valid @RequestBody AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
//        return ResponseEntity.ok(alunoService.post(alunoCreateDTO));}
//
//    @Operation(summary = "Atualizar aluno", description = "Atualiza aluno existente no banco de dados")
//    @Response
//    @PutMapping("{idAluno}")
//    public ResponseEntity<AlunoDTO> update(@PathVariable("idAluno") Integer id,
//                                             @Valid@RequestBody AlunoUpdateDTO alunoAtualizar) throws RegraDeNegocioException {
//        return ResponseEntity.ok(alunoService.put(id, alunoAtualizar));
//    }
//
//    @Operation(summary = "Deletar aluno", description = "Deleta aluno existente no banco de dados")
//    @Response
//    @DeleteMapping("/{idAluno}")
//    public void delete (@PathVariable("idAluno") Integer id) throws RegraDeNegocioException {
//        alunoService.removerAluno(id);
//    }
//}