package br.com.dbccompany.time7.gestaodeensino.service;


import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.EnderecoDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.Aluno;
import br.com.dbccompany.time7.gestaodeensino.entity.Endereco;
import br.com.dbccompany.time7.gestaodeensino.entity.Professor;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.EnderecoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.ProfessorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
            if (containsEndereco(enderecoCreateDTO).getIdEndereco() == null) {
                log.info("Endereço adicionado");
                return enderecoToDTO(enderecoRepository.adicionar(createToEndereco(enderecoCreateDTO)));
            } else {
                throw new RegraDeNegocioException("O endereço já existe no banco de dados");
            }
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException("Falha ao adicionar o endereço");
        }
    }


    public Endereco containsEndereco(EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        return enderecoRepository.containsEndereco(enderecoCreateDTO);
    }

    public EnderecoDTO putEndereco(Integer idEndereco, EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        log.info("Atualizando endereço");
        EnderecoDTO enderecoDTO = enderecoToDTO(createToEndereco(enderecoCreateDTO));
        enderecoDTO.setIdEndereco(idEndereco);

        if (enderecoRepository.editar(idEndereco, createToEndereco(enderecoCreateDTO))) {
            log.info("Endereço atualizado");
            return enderecoDTO;
        } else {
            throw new RegraDeNegocioException("Falha ao atualizar endereço");
        }
    }

    public void deleteEndereco(Integer idEndereco) throws RegraDeNegocioException {
        log.info("Removendo endereço");
        try {
            List<Aluno> quantidadeAlunosComIdEndereco = alunoRepository.conferirAlunosComIdEndereco(idEndereco);
            List<Professor> quantidadeProfessoresComIdEndereco = professorRepository.conferirColaboradoresComIdEndereco(idEndereco);
            if (quantidadeProfessoresComIdEndereco.size() + quantidadeAlunosComIdEndereco.size() == 0) {
                enderecoRepository.remover(idEndereco);
                log.info("Endereço removido");
            }
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    public Endereco createToEndereco(EnderecoCreateDTO enderecoCreateDTO) {
        return objectMapper.convertValue(enderecoCreateDTO, Endereco.class);
    }

    public EnderecoDTO enderecoToDTO(Endereco endereco) {
        return objectMapper.convertValue(endereco, EnderecoDTO.class);
    }
}
