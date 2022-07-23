package br.com.dbccompany.time7.gestaodeensino.service;


import br.com.dbccompany.time7.gestaodeensino.dto.notas.NotaUpdateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.notas.NotaDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.AlunoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.CursoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.DisciplinaEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.NotaEntity;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.CursoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.NotaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NotaService {

    private final NotaRepository notaRepository;

    private final AlunoRepository alunoRepository;

    private final CursoRepository cursoRepository;
    private final ObjectMapper objectMapper;


    public List<NotaDTO> findByIdAluno(Integer idAluno) {
        log.info("Listando notas por ID do aluno");

        return notaRepository.findAllByAlunoEntity_IdAluno(idAluno).stream()
                .map(this::entityToDTO).toList();
    }
    public void adicionarNotasAluno(Integer idCurso, Integer idAluno) throws RegraDeNegocioException {
        CursoEntity cursoEntityRecuperado = cursoRepository.findById(idCurso).orElseThrow(() -> new RegraDeNegocioException("Curso não encontrado"));
        AlunoEntity alunoEntityRecuperado = alunoRepository.findById(idAluno).orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado"));

        log.info("Criando notas para " + alunoEntityRecuperado.getNome() + "...");

        cursoEntityRecuperado.getDisciplinasEntities().stream().forEach(disciplinaEntity -> {
            NotaEntity notaEntityNova = new NotaEntity();
            notaEntityNova.setAlunoEntity(alunoEntityRecuperado);
            notaEntityNova.setDisciplinaEntity(disciplinaEntity);
            notaRepository.save(notaEntityNova);
        });

        log.info("Notas adicionadas");
    }

    public void adicionarNotasAlunosDoCursoByDisciplina(DisciplinaEntity disciplinaEntity, CursoEntity cursoEntity){
        cursoEntity.getAlunosEntities().stream().forEach(alunoEntity -> {
            NotaEntity notaEntityNova = new NotaEntity();
            notaEntityNova.setAlunoEntity(alunoEntity);
            notaEntityNova.setDisciplinaEntity(disciplinaEntity);
            notaRepository.save(notaEntityNova);
        });
    }

    public void deletarNotasAlunosDoCursoByDisciplina(DisciplinaEntity disciplinaEntity){
        notaRepository.deleteAllByDisciplinaEntityContains(disciplinaEntity);
    }

    @Transactional
    public void deleteAllNotasByIdAluno(Integer idAluno) {
        notaRepository.deleteAllByAlunoEntity_IdAluno(idAluno);
    }

    public NotaDTO atualizarNotasAluno(Integer idNota, NotaUpdateDTO notaUpdateDTO) throws RegraDeNegocioException {
        log.info("Atualizando notas de aluno");
        Integer divisor = 0;
        Double media = 0.0;

        NotaEntity notaEntityRecuperada = notaRepository.findById(idNota)
                .orElseThrow(() -> new RegraDeNegocioException("Nota não encontrada"));

        NotaEntity notaEntityAtualizar = updateToEntity(notaUpdateDTO);

        if (notaEntityAtualizar.getNota1() == null){
            notaEntityAtualizar.setNota1(notaEntityRecuperada.getNota1());
        }

        if (notaEntityAtualizar.getNota2() == null){
            notaEntityAtualizar.setNota2(notaEntityRecuperada.getNota2());
        }

        if (notaEntityAtualizar.getNota3() == null){
            notaEntityAtualizar.setNota3(notaEntityRecuperada.getNota3());
        }

        if (notaEntityAtualizar.getNota4() == null){
            notaEntityAtualizar.setNota4(notaEntityRecuperada.getNota4());
        }

        if (notaEntityAtualizar.getNota1() != null) {
            divisor += 1;
            media += notaEntityAtualizar.getNota1();
        }

        if (notaEntityAtualizar.getNota2() != null) {
            divisor += 1;
            media += notaEntityAtualizar.getNota2();
        }

        if (notaEntityAtualizar.getNota3() != null) {
            divisor += 1;
            media += notaEntityAtualizar.getNota3();
        }

        if (notaEntityAtualizar.getNota4() != null) {
            divisor += 1;
            media += notaEntityAtualizar.getNota4();
        }

        if (divisor > 0){
            media /= divisor;
        }

        notaEntityAtualizar.setIdNota(idNota);
        notaEntityAtualizar.setMedia(media);
        notaEntityAtualizar.setDisciplinaEntity(notaEntityRecuperada.getDisciplinaEntity());
        notaEntityAtualizar.setAlunoEntity(notaEntityRecuperada.getAlunoEntity());

        NotaDTO notaDTO = entityToDTO(notaRepository.save(notaEntityAtualizar));

        log.info("Nota adicionada");
        return notaDTO;
    }

     public NotaEntity updateToEntity(NotaUpdateDTO notaUpdateDTO) {
        return objectMapper.convertValue(notaUpdateDTO, NotaEntity.class);
    }

    public NotaDTO entityToDTO(NotaEntity nota) {
        return objectMapper.convertValue(nota, NotaDTO.class);
    }
}
