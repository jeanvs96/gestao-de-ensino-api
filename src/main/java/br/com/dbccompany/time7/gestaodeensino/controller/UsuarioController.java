package br.com.dbccompany.time7.gestaodeensino.controller;

import br.com.dbccompany.time7.gestaodeensino.dto.usuario.UsuarioDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.usuario.UsuarioLoginDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.usuario.UsuarioRecuperarSenhaDTO;
import br.com.dbccompany.time7.gestaodeensino.entity.UsuarioEntity;
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

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Validated
public class UsuarioController {
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

    @PostMapping("/cadastro")
    public ResponseEntity<String> createUser(@RequestBody @Valid UsuarioLoginDTO usuarioLoginDTO) {
        return new ResponseEntity<>(usuarioService.saveUsuario(usuarioLoginDTO).getLogin(), HttpStatus.OK);
    }

    @GetMapping("/logged")
    public ResponseEntity<UsuarioDTO> getUsuarioLogado() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(), HttpStatus.OK);
    }

    @GetMapping("/recuperar-senha/{login}")
    public ResponseEntity<String> recuperarSenha(@PathVariable ("login") String login) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.recuperarSenha(login), HttpStatus.OK);
    }

    @PutMapping("/alterar-senha")
    public ResponseEntity<UsuarioDTO> alterarSenha(@RequestBody @Valid UsuarioRecuperarSenhaDTO usuarioRecuperarSenhaDTO,
                                                   @RequestParam ("value") String token) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.alterarSenha(token, usuarioRecuperarSenhaDTO), HttpStatus.OK);
    }
}
