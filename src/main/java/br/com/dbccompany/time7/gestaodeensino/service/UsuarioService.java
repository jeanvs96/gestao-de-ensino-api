package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.usuario.UsuarioAtivarDesativarDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.usuario.UsuarioDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.usuario.UsuarioLoginDTO;
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
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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

    public PessoaEntity findPessoaByIdUsuario(Integer idUsuario){
        Optional<AlunoEntity> alunoEntityOptional = alunoRepository.findByIdUsuario(idUsuario);
        if(alunoEntityOptional.isPresent()) {
            return alunoEntityOptional.get();
        }
        Optional<ProfessorEntity> professorEntityOptional = professorRepository.findByIdUsuario(idUsuario);
        if (professorEntityOptional.isPresent()) {
            return professorEntityOptional.get();
        }
        return null;
    }

    public String validarTokenRecuperarSenha(String token) throws RegraDeNegocioException {
        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        Date now = new Date();
        if (body.getExpiration().after(now)) {
            return TokenAuthenticationFilter.BEARER + token;
        } else {
            return "Token inválido. Solicite novo link para alterar senha";
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
        usuarioEntityRecuperado.setLogin(usuarioEntityRecuperado.getLogin());
        usuarioEntityRecuperado.setRolesEntities(usuarioEntityRecuperado.getRolesEntities());


        return "Ativado";
    }
}
