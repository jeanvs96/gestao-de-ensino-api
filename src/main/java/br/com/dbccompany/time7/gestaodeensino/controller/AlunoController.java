package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.AlunoDTO;
import br.com.dbccompany.time7.gestaodeensino.service.AlunoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/aluno")
public class AlunoController {
    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public List<AlunoDTO> list(){
        return alunoService.listarAlunos();
    }

    @GetMapping("/{idAluno}")  //localhost:8080/aluno/1
    public List<AlunoDTO> listById(@PathVariable("idAluno") Integer id) throws Exception {
        return (List<AlunoDTO>) alunoService.imprimirInformacoesDoAluno(id);
    }


}
