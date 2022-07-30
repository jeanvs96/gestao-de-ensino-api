package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.documentation.UsuarioDocumentation;
import br.com.dbccompany.time7.gestaodeensino.dto.relatorios.RelatorioUsuariosDoSistemaDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.usuario.*;
import br.com.dbccompany.time7.gestaodeensino.entity.UsuarioEntity;
import br.com.dbccompany.time7.gestaodeensino.enums.AtivarDesativarUsuario;
import br.com.dbccompany.time7.gestaodeensino.enums.TipoPessoa;
import br.com.dbccompany.time7.gestaodeensino.exceptions.RegraDeNegocioException;
import br.com.dbccompany.time7.gestaodeensino.security.TokenService;
import br.com.dbccompany.time7.gestaodeensino.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Validated
public class UsuarioController implements UsuarioDocumentation {
    private final UsuarioService usuarioService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(@RequestBody @Valid UsuarioLoginDTO usuarioLoginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        usuarioLoginDTO.getLogin(),
                        usuarioLoginDTO.getSenha()
                );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return tokenService.getToken((UsuarioEntity) authentication.getPrincipal());
    }

    @PostMapping("/cadastro-admin")
    public ResponseEntity<UsuarioDTO> createUserAdmin(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.saveUsuarioAdmin(usuarioCreateDTO), HttpStatus.OK);
    }

    @PostMapping("/cadastro-aluno")
    public ResponseEntity<UsuarioDTO> createUserAluno(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.saveUsuarioAluno(usuarioCreateDTO), HttpStatus.OK);
    }

    @PostMapping("/cadastro-professor")
    public ResponseEntity<UsuarioDTO> createUserProfessor(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.saveUsuarioProfessor(usuarioCreateDTO), HttpStatus.OK);
    }

    @GetMapping("/logged")
    public ResponseEntity<UsuarioDTO> getUsuarioLogado() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(), HttpStatus.OK);
    }

    @GetMapping("/recuperar-senha/{login}")
    public ResponseEntity<String> recuperarSenha(@PathVariable ("login") String login) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.recuperarSenha(login), HttpStatus.OK);
    }

    @GetMapping("/recuperar-senha/valid")
    public ResponseEntity<String> validarTokenRecuperarSenha(@RequestParam ("token") String token) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.validarTokenRecuperarSenha(token), HttpStatus.OK);
    }

    @GetMapping("/listar-usuarios-pessoas")
    public ResponseEntity<List<RelatorioUsuariosDoSistemaDTO>> listarUsuariosDoSistema(@RequestParam TipoPessoa tipoPessoa) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.listarUsuariosDoSistema(tipoPessoa), HttpStatus.OK);
    }

    @PutMapping("/alterar-senha")
    public ResponseEntity<UsuarioDTO> updateSenha(@RequestBody @Valid UsuarioRecuperarSenhaDTO usuarioRecuperarSenhaDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.updateRecuperarSenha(usuarioRecuperarSenhaDTO), HttpStatus.OK);
    }

    @PutMapping("/ativar-desativar-usuario/{idUsuario}")
    public ResponseEntity<String> ativarDesativarUsuario(@PathVariable("idUsuario") @Valid Integer idUsuario, @RequestParam AtivarDesativarUsuario ativarDesativarUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.ativarDesativarUsuario(idUsuario, ativarDesativarUsuario), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UsuarioDTO> updateUsuario(@RequestBody @Valid UsuarioUpdateDTO usuarioUpdateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.update(usuarioUpdateDTO), HttpStatus.OK);
    }
}
