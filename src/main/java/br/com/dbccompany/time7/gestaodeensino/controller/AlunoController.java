package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.AlunoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.AlunoDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Aluno;
import br.com.dbccompany.time7.gestaodeensino.service.AlunoService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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

    @GetMapping
    public List<AlunoDTO> list() {
        return alunoService.listarAlunos();
    }

    @GetMapping("/{idAluno}")  //localhost:8080/aluno/1
    public List<AlunoDTO> listById(@PathVariable("idAluno") Integer id) throws Exception {
        return (List<AlunoDTO>) alunoService.getAlunoById(id);
    }

    @PostMapping("{idAluno}")
    public ResponseEntity<AlunoDTO> create (@PathVariable("idAluno") Integer id, @Valid @RequestBody AlunoCreateDTO aluno) throws Exception {
        return ResponseEntity.ok(alunoService.adicionarAluno(id, aluno));}

    @PutMapping("{idAluno}")
    public ResponseEntity<AlunoDTO> update(@PathVariable("idAluno") Integer id,
                                             @Valid@RequestBody AlunoCreateDTO alunoAtualizar) throws Exception {
        return ResponseEntity.ok(alunoService.editarAluno(id, alunoAtualizar));
    }

    @DeleteMapping("/{IdAluno}")
    public void delete (PathVariable("idAluno") Integer id) throws Exception {
        alunoService.removerAluno(id);
    }
}