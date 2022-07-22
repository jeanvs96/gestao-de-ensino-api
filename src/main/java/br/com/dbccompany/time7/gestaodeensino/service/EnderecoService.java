package br.com.dbccompany.time7.gestaodeensino.service;


import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoUpdateDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.AlunoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.EnderecoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.ProfessorEntity;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repositoryOLD.AlunoRepository;
import br.com.dbccompany.time7.gestaodeensino.repositoryOLD.EnderecoRepository;
import br.com.dbccompany.time7.gestaodeensino.repositoryOLD.ProfessorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final ObjectMapper objectMapper;

    public EnderecoDTO getById(Integer idEndereco) throws RegraDeNegocioException {
        log.info("Listando endereços");
        return enderecoToDTO(enderecoRepository.pegarEnderecoPorId(idEndereco));
    }

    public EnderecoDTO postEndereco(EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        log.info("Adicionando endereços");
        try {
                EnderecoDTO enderecoDTO = enderecoToDTO(enderecoRepository.adicionar(createToEndereco(enderecoCreateDTO)));
                log.info("Endereço adicionado");
                return enderecoDTO;
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException("Falha ao adicionar o endereço");
        }
    }

    public EnderecoDTO putEndereco(Integer idEndereco, EnderecoUpdateDTO enderecoUpdateDTO) throws RegraDeNegocioException {
        log.info("Atualizando endereço");

        if (enderecoRepository.editar(idEndereco, updateToEndereco(enderecoUpdateDTO))) {
            log.info("Endereço atualizado");
            return enderecoToDTO(enderecoRepository.pegarEnderecoPorId(idEndereco));
        } else {
            throw new RegraDeNegocioException("Falha ao atualizar endereço");
        }
    }

    public void deleteEndereco(Integer idEndereco) throws RegraDeNegocioException {
        log.info("Removendo endereço");
        try {
            List<AlunoEntity> quantidadeAlunosComIdEndereco = alunoRepository.conferirAlunosComIdEndereco(idEndereco);
            List<ProfessorEntity> quantidadeProfessoresComIdEndereco = professorRepository.conferirColaboradoresComIdEndereco(idEndereco);
            if (quantidadeProfessoresComIdEndereco.size() + quantidadeAlunosComIdEndereco.size() == 0) {
                enderecoRepository.remover(idEndereco);
                log.info("Endereço removido");
            }
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    public EnderecoEntity createToEndereco(EnderecoCreateDTO enderecoCreateDTO) {
        return objectMapper.convertValue(enderecoCreateDTO, EnderecoEntity.class);
    }

    public EnderecoDTO enderecoToDTO(EnderecoEntity endereco) {
        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }

    public EnderecoEntity updateToEndereco(EnderecoUpdateDTO enderecoUpdateDTO) {
        return objectMapper.convertValue(enderecoUpdateDTO, EnderecoEntity.class);
    }
}
