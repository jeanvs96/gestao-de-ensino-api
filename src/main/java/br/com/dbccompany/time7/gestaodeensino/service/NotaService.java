package br.com.dbccompany.time7.gestaodeensino.service;


import br.com.dbccompany.time7.gestaodeensino.dto.NotaUpdateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.NotaDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.AlunoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.CursoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.NotaEntity;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.NotaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NotaService {

    private final NotaRepository notaRepository;

    private final AlunoRepository alunoRepository;

    private final CursoService cursoService;
    private final ObjectMapper objectMapper;


    public List<NotaDTO> findByIdAluno(Integer idAluno) {
        log.info("Listando notas por ID do aluno");

        return notaRepository.findAllByAlunoEntity_IdAluno(idAluno).stream()
                .map(this::entityToDTO).toList();
    }
    public void adicionarNotasAluno(Integer idCurso, Integer idAluno) throws RegraDeNegocioException {
        CursoEntity cursoEntityRecuperado = cursoService.findById(idCurso);
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

        notaEntityAtualizar.setDisciplinaEntity(notaEntityRecuperada.getDisciplinaEntity());
        notaEntityAtualizar.setAlunoEntity(notaEntityRecuperada.getAlunoEntity());

        NotaEntity notaEntityAtualizada = notaRepository.save(notaEntityAtualizar);

        if (notaEntityAtualizada.getNota1() != null) {
            divisor += 1;
            media += notaEntityAtualizada.getNota1();
        }

        if (notaEntityAtualizada.getNota2() != null) {
            divisor += 1;
            media += notaEntityAtualizada.getNota2();
        }

        if (notaEntityAtualizada.getNota3() != null) {
            divisor += 1;
            media += notaEntityAtualizada.getNota3();
        }

        if (notaEntityAtualizada.getNota4() != null) {
            divisor += 1;
            media += notaEntityAtualizada.getNota4();
        }

        media /= divisor;
        notaEntityAtualizada.setMedia(media);

        NotaDTO notaDTO = entityToDTO(notaRepository.save(notaEntityAtualizada));

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
