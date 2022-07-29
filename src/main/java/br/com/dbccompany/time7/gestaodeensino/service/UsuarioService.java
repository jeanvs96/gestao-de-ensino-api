package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.usuario.UsuarioCreateDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.usuario.UsuarioDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.usuario.UsuarioRecuperarSenhaDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.AlunoEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.PessoaEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.ProfessorEntity;
import br.com.dbccompany.time7.gestaodeensino.entity.UsuarioEntity;
import br.com.dbccompany.time7.gestaodeensino.enums.AtivarDesativarUsuario;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.repository.AlunoRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.ProfessorRepository;
import br.com.dbccompany.time7.gestaodeensino.repository.UsuarioRepository;
import br.com.dbccompany.time7.gestaodeensino.security.TokenAuthenticationFilter;
import br.com.dbccompany.time7.gestaodeensino.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    @Value("${jwt.secret}")
    private String secret;
    private static final String RECUPERAR_SENHA_URL = "http://localhost:8080/usuario/recuperar-senha/valid?token=";
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final RolesService rolesService;

    public UsuarioEntity findById(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
    }

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public UsuarioDTO saveUsuarioAdmin(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = createToEntity(usuarioCreateDTO);
        usuarioEntity.setRolesEntities(Set.of(rolesService.findByRole("ROLE_ADMIN")));
        return entityToDto(usuarioRepository.save(usuarioEntity));
    }

    public UsuarioDTO saveUsuarioProfessor(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = createToEntity(usuarioCreateDTO);
        usuarioEntity.setRolesEntities(Set.of(rolesService.findByRole("ROLE_PROFESSOR")));
        return entityToDto(usuarioRepository.save(usuarioEntity));
    }

    public UsuarioDTO saveUsuarioAluno(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = createToEntity(usuarioCreateDTO);
        usuarioEntity.setRolesEntities(Set.of(rolesService.findByRole("ROLE_ALUNO")));
        return entityToDto(usuarioRepository.save(usuarioEntity));
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

    public UsuarioEntity createToEntity(UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        usuarioEntity.setStatus(true);
        encodePassword(usuarioEntity);
        return usuarioEntity;
    }

    public String recuperarSenha(String login) throws RegraDeNegocioException {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByLogin(login);
        if (usuarioEntity.isPresent()) {
            PessoaEntity pessoaEntity = findPessoaByIdUsuario(usuarioEntity.get().getIdUsuario());

            String token = tokenService.getToken(usuarioEntity.get());
            String tokenReplace = token.replace(TokenAuthenticationFilter.BEARER, "");
            String url = RECUPERAR_SENHA_URL + tokenReplace;

            emailService.sendEmailAlterarSenha(pessoaEntity, url);

            return "Enviado email com instruções para recuperar senha";
        } else {
            return "Usuário não encontrado";
        }

    }

    public PessoaEntity findPessoaByIdUsuario(Integer idUsuario) {
        Optional<AlunoEntity> alunoEntityOptional = alunoRepository.findByIdUsuario(idUsuario);
        if (alunoEntityOptional.isPresent()) {
            return alunoEntityOptional.get();
        }
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findByIdUsuario(idUsuario);
        if (professorEntityOptional.isPresent()) {
            return professorEntityOptional.get();
        }
        return null;
    }

    public String validarTokenRecuperarSenha(String token) throws RegraDeNegocioException {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return TokenAuthenticationFilter.BEARER + token;
        } catch (Exception e) {
            throw new RegraDeNegocioException("Token inválido. Solicite novo link para alterar senha");
        }
    }

    public UsuarioDTO updateSenha(UsuarioRecuperarSenhaDTO usuarioRecuperarSenhaDTO) throws RegraDeNegocioException {
        Integer idUsuario = getIdLoggedUser();
        UsuarioEntity usuarioEntity = findById(idUsuario);
        usuarioEntity.setRolesEntities(usuarioEntity.getRolesEntities());
        usuarioEntity.setSenha(usuarioRecuperarSenhaDTO.getSenha());
        encodePassword(usuarioEntity);

        return entityToDto(usuarioRepository.save(usuarioEntity));
    }

    public String ativarDesativarUsuario(Integer idUsuario, AtivarDesativarUsuario ativarDesativarUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntityRecuperado = findById(idUsuario);

        if (ativarDesativarUsuario.equals(AtivarDesativarUsuario.ATIVAR)) {
            usuarioEntityRecuperado.setStatus(true);

            usuarioRepository.save(usuarioEntityRecuperado);

            return "Ativado";
        } else {
            usuarioEntityRecuperado.setStatus(false);

            usuarioRepository.save(usuarioEntityRecuperado);

            return "Desativo";
        }
    }
}
