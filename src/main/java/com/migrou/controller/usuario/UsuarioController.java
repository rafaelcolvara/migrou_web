package com.migrou.controller.usuario;

import com.migrou.implementacoes.auth.TokenService;
import com.migrou.interfaces.pessoas.PessoaInterface;
import com.migrou.interfaces.usuario.UsuarioJPA;
import com.migrou.types.dto.LoginDTO;
import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.entity.Usuario;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/usuario/")
public class UsuarioController {

    @Autowired
    UsuarioJPA usuarioJPA;

    @Autowired
    PessoaInterface pessoaInterface;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Login via email e senha ")
    public ResponseEntity<?> login(HttpServletRequest req, HttpServletResponse resp, @RequestBody @Valid final LoginDTO loginDTO) {

        UsernamePasswordAuthenticationToken dadosLogin = loginDTO.converter();
        try {

            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            System.out.println("Vai chamar a consulta de usuario:" + loginDTO.getUsername() + " - " + loginDTO.getTipoPessoa());
            PessoaDTO pessoaDTO = pessoaInterface.consultaPorEmaileTipoPessoa(loginDTO.getUsername(), loginDTO.getTipoPessoa());
            System.out.println("buscou o cara: " + pessoaDTO.getNome());

            pessoaDTO.setToken(token);

            return ResponseEntity.ok().body(pessoaDTO);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("AUTHENTICATION_ERROR");
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_GATEWAY);
        }
    }

}
