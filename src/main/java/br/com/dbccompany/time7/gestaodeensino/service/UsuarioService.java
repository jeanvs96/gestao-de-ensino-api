package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.usuario.UsuarioDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.usuario.UsuarioLoginDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.UsuarioEntity;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    public UsuarioEntity findById(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
    }

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public UsuarioEntity saveUsuario(UsuarioLoginDTO usuarioLoginDTO) {
        UsuarioEntity usuarioEntity = loginToEntity(usuarioLoginDTO);
        encodePassword(usuarioEntity);
        return usuarioRepository.save(usuarioEntity);
    }

    public void encodePassword(UsuarioEntity usuarioEntity) {
        usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getPassword()));
    }

    public UsuarioDTO getLoggedUser() throws RegraDeNegocioException {
        return entityToDto(findById(getIdLoggedUser()));
    }

    public Integer getIdLoggedUser() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UsuarioDTO entityToDto(UsuarioEntity usuarioEntity) {
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

    public UsuarioEntity loginToEntity(UsuarioLoginDTO usuarioLoginDTO) {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioLoginDTO, UsuarioEntity.class);
        usuarioEntity.setStatus(true);
        return usuarioEntity;
    }
}
