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
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final ObjectMapper objectMapper;

    public EnderecoDTO getById(Integer idEndereco) throws RegraDeNegocioException {
        return enderecoToDTO(enderecoRepository.pegarEnderecoPorId(idEndereco));
    }

    public EnderecoDTO postEndereco(EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException {
        try {
            if (containsEndereco(enderecoCreateDTO).isEmpty()) {
                return enderecoToDTO(enderecoRepository.adicionar(createToEndereco(enderecoCreateDTO)));
            } else {
                throw new RegraDeNegocioException("O endereço já existe no banco de dados");
            }
        } catch (SQLException e) {
            throw new RegraDeNegocioException("Falha ao adicionar o endereço");
        }
    }


    public Optional<Endereco> containsEndereco(EnderecoCreateDTO enderecoCreateDTO) throws SQLException {
        Optional<Endereco> enderecoOptional = Optional.of(enderecoRepository.containsEndereco(enderecoCreateDTO));

        return enderecoOptional;
    }

    public EnderecoDTO putEndereco(Integer idEndereco, EnderecoCreateDTO enderecoCreateDTO) throws RegraDeNegocioException, SQLException {
        EnderecoDTO enderecoDTO = enderecoToDTO(createToEndereco(enderecoCreateDTO));
        enderecoDTO.setIdEndereco(idEndereco);

        if (enderecoRepository.editar(idEndereco, createToEndereco(enderecoCreateDTO))) {
            return enderecoDTO;
        } else {
            throw new RegraDeNegocioException("Falha ao atualizar endereço");
        }
    }

    public void deleteEndereco(Integer idEndereco) throws RegraDeNegocioException {
        try {
            List<Aluno> quantidadeAlunosComIdEndereco = alunoRepository.conferirAlunosComIdEndereco(idEndereco);
            List<Professor> quantidadeProfessoresComIdEndereco = professorRepository.conferirColaboradoresComIdEndereco(idEndereco);
            if (quantidadeProfessoresComIdEndereco.size() + quantidadeAlunosComIdEndereco.size() == 0) {
                enderecoRepository.remover(idEndereco);
            }
        } catch (SQLException e) {
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
