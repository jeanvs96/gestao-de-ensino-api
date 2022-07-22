package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoUpdateDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.EnderecoEntity;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;

    public EnderecoDTO findById(Integer idEndereco) throws RegraDeNegocioException {
        log.info("Listando endereço");

        return entityToDTO(enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado")));
    }

    public EnderecoDTO save(EnderecoCreateDTO enderecoCreateDTO) {
        log.info("Adicionando endereços");

        EnderecoDTO enderecoDTO = entityToDTO(enderecoRepository.save(createToEntity(enderecoCreateDTO)));

        log.info("Endereço adicionado");

        return enderecoDTO;
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoUpdateDTO enderecoUpdateDTO) {
        log.info("Atualizando endereço");

        EnderecoEntity enderecoEntity = updateToEntity(enderecoUpdateDTO);

        enderecoEntity.setIdEndereco(idEndereco);
        enderecoEntity.setAlunoEntity(enderecoEntity.getAlunoEntity());
        enderecoEntity.setProfessorEntity(enderecoEntity.getProfessorEntity());

        EnderecoDTO enderecoDTO = entityToDTO(enderecoRepository.save(enderecoEntity));

        log.info("Endereço atualizado");

        return enderecoDTO;
    }

    public void delete(Integer idEndereco) throws RegraDeNegocioException {
        log.info("Removendo endereço");

        EnderecoEntity enderecoDelete = enderecoRepository.findById(idEndereco).
                orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));

        enderecoDelete.setProfessorEntity(enderecoDelete.getProfessorEntity());
        enderecoDelete.setAlunoEntity(enderecoDelete.getAlunoEntity());

        enderecoRepository.delete(enderecoDelete);

        log.info("Endereço removido");
    }

    public EnderecoEntity createToEntity(EnderecoCreateDTO enderecoCreateDTO) {
        return objectMapper.convertValue(enderecoCreateDTO, EnderecoEntity.class);
    }

    public EnderecoDTO entityToDTO(EnderecoEntity endereco) {
        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }

    public EnderecoEntity updateToEntity(EnderecoUpdateDTO enderecoUpdateDTO) {
        return objectMapper.convertValue(enderecoUpdateDTO, EnderecoEntity.class);
    }
}
