package br.com.dbccompany.time7.gestaodeensino.service;


import br.com.dbccompany.time7.gestaodeensino.dto.NotaCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.NotaDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Nota;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.DisciplinaXCursoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.NotaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotaService {

    private final NotaRepository notaRepository;

    private final DisciplinaXCursoRepository disciplinaXCursoRepository;

    private final ObjectMapper objectMapper;


    public List<NotaDTO> listByIdAluno(Integer idAluno) throws RegraDeNegocioException {
        return notaRepository.listarPorAluno(idAluno).stream().map(nota -> notaToNotaDTO(nota)).toList();
    }
    public void adicionarNotasAluno(Integer idCurso, Integer idAluno) throws RegraDeNegocioException {
        disciplinaXCursoRepository.listarPorCurso(idCurso).stream()
                .map(disciplinaXCurso -> disciplinaXCurso.getIdDisciplina())
                .forEach(idDisciplina -> {
                    Nota nota = new Nota();
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
        Integer divisor = 0;
        Double media = 0.0;

        NotaDTO notaDTO = createToNotaDTO(notaCreateDTO);
        notaRepository.atualizarNotasDisciplina(idNota, notaCreateDTO);

        Nota nota = notaRepository.listNotaById(idNota);
        if (nota.getNota1() != null) {
            divisor += 1;
            media += nota.getNota1();
            notaDTO.setNota1(nota.getNota1());
        }

        if (nota.getNota2() != null) {
            divisor += 1;
            media += nota.getNota1();
            notaDTO.setNota2(nota.getNota2());
        }

        if (nota.getNota3() != null) {
            divisor += 1;
            media += nota.getNota1();
            notaDTO.setNota3(nota.getNota3());
        }

        if (nota.getNota4() != null) {
            divisor += 1;
            media += nota.getNota1();
            notaDTO.setNota4(nota.getNota4());
        }

        media /= divisor;
        notaDTO.setMedia(media);

        notaRepository.atualizarMediaDisciplina(idNota, media);

        return notaDTO;
    }

    public NotaDTO createToNotaDTO(NotaCreateDTO notaCreateDTO) {
        return objectMapper.convertValue(notaCreateDTO, NotaDTO.class);
    }

    public NotaDTO notaToNotaDTO(Nota nota) {
        return objectMapper.convertValue(nota, NotaDTO.class);
    }
}
