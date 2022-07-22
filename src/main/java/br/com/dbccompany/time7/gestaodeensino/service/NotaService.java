package br.com.dbccompany.time7.gestaodeensino.service;


import br.com.dbccompany.time7.gestaodeensino.dto.NotaCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.NotaDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.NotaEntity;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.DisciplinaXCursoRepository;
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

    private final DisciplinaXCursoRepository disciplinaXCursoRepository;

    private final ObjectMapper objectMapper;


    public List<NotaDTO> listByIdAluno(Integer idAluno) throws RegraDeNegocioException {
        log.info("Listando notas por ID do aluno");
        return notaRepository.listarPorAluno(idAluno).stream().map(nota -> notaToNotaDTO(nota)).toList();
    }
    public void adicionarNotasAluno(Integer idCurso, Integer idAluno) throws RegraDeNegocioException {
        disciplinaXCursoRepository.listarPorCurso(idCurso).stream()
                .map(disciplinaXCurso -> disciplinaXCurso.getIdDisciplina())
                .forEach(idDisciplina -> {
                    NotaEntity nota = new NotaEntity();
                    nota.setIdDisciplina(idDisciplina);
                    nota.setIdAluno(idAluno);
                    try {
                        notaRepository.adicionarNotasAluno(nota);
                    } catch (RegraDeNegocioException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public NotaDTO atualizarNotasAluno(Integer idNota, NotaCreateDTO notaCreateDTO) throws RegraDeNegocioException {
        log.info("Atualizando notas de aluno");
        Integer divisor = 0;
        Double media = 0.0;

        notaRepository.atualizarNotasDisciplina(idNota, notaCreateDTO);

        NotaDTO notaDTO = notaToNotaDTO(notaRepository.listNotaById(idNota));
        if (notaDTO.getNota1() != null) {
            divisor += 1;
            media += notaDTO.getNota1();
        }

        if (notaDTO.getNota2() != null) {
            divisor += 1;
            media += notaDTO.getNota2();
        }

        if (notaDTO.getNota3() != null) {
            divisor += 1;
            media += notaDTO.getNota3();
        }

        if (notaDTO.getNota4() != null) {
            divisor += 1;
            media += notaDTO.getNota4();
        }

        media /= divisor;
        notaDTO.setMedia(media);

        notaRepository.atualizarMediaDisciplina(idNota, media);
        log.info("Nota adicionada");
        return notaDTO;
    }

    public NotaDTO createToNotaDTO(NotaCreateDTO notaCreateDTO) {
        return objectMapper.convertValue(notaCreateDTO, NotaDTO.class);
    }

    public NotaDTO notaToNotaDTO(NotaEntity nota) {
        return objectMapper.convertValue(nota, NotaDTO.class);
    }
}
