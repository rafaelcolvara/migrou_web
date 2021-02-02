package com.migrou.controller.usuario;

import com.migrou.implementacoes.auth.TokenService;
import com.migrou.implementacoes.pessoas.UsuarioImpl;
import com.migrou.implementacoes.pessoas.bo.ClienteBO;
import com.migrou.implementacoes.pessoas.bo.UsuarioBO;
import com.migrou.implementacoes.pessoas.bo.VendedorBO;
import com.migrou.interfaces.cliente.ClienteInterface;
import com.migrou.interfaces.usuario.UsuarioJPA;
import com.migrou.interfaces.vendedor.VendedorInterface;
import com.migrou.types.dto.*;
import com.migrou.types.entity.Usuario;
import com.migrou.types.entity.VendedorEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioJPA usuarioJPA;

    @Autowired
    UsuarioImpl usuarioImpl;

    @Autowired
    VendedorInterface vendedorInterface;

    @Autowired
    ClienteInterface clienteInterface;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Login via email e senha ")
    public ResponseEntity<?> login(HttpServletRequest req, HttpServletResponse resp, @RequestBody @Valid final LoginDTO loginDTO) {

        UsernamePasswordAuthenticationToken dadosLogin = loginDTO.converter();
        try {
            PessoaDTO pessoaDTO = new PessoaDTO();
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            Usuario usuario = usuarioJPA.findByUsername(loginDTO.getUsername()).orElseThrow(() -> new Exception("Usuario nao encontrado"));
            if (usuario.getAuthorities().stream().filter(x -> x.getAuthority().equals(loginDTO.getTipoPessoa())).count()==0){
                throw new Exception("Perfil nao encontrado para este usuário");
            }
            if (loginDTO.getTipoPessoa().equals("VENDEDOR")){
                VendedorDTO vendedorDTO = new VendedorDTO();
                vendedorDTO = vendedorInterface.consultaVendedorPorId(loginDTO.getUsername());
                pessoaDTO = new VendedorBO().parseVendedorDTOToPessoaDTO(vendedorDTO);
            } else {
                ClienteDTO clienteDTO = new ClienteDTO();
                clienteDTO = clienteInterface.consultaClienteporID(loginDTO.getUsername());
                pessoaDTO = new ClienteBO().parseClienteDTOPessoaDTO(clienteDTO);
            }

            pessoaDTO.setToken(token);

            return ResponseEntity.ok().body(pessoaDTO);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("AUTHENTICATION_ERROR");
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping(value = "/inclui", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Cadastro de usuários do Migrou ")
    public ResponseEntity<?> incluirUsuario(@RequestBody PessoaDTO pessoaDTO) throws Exception {

        try {
            Usuario usuario = usuarioImpl.salva(pessoaDTO);
            PessoaDTO pessoa = new PessoaDTO();
            UsuarioBO.parseUsuarioEntityPessoaDTO(usuario);
        }catch (Exception e){
            if (e.getMessage().contains("ja cadastrado")) {
                return ResponseEntity.badRequest().body(pessoaDTO.getTipoPessoa() + " ja cadastrado" );
            }else {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(e.getMessage() );
            }
        }

        return ResponseEntity.ok().body(pessoaDTO);
    }

    @PatchMapping(value = "/foto", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Cadastro de usuários do Migrou ")
    public ResponseEntity<String> atualizaFoto(@RequestBody PessoaFotoDTO pessoaFotoDTO) throws Exception {

        try {
            Usuario usuario = usuarioImpl.salvaFoto(pessoaFotoDTO);

        }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage() );
        }

        return ResponseEntity.ok().body("Atualizado com sucesso!");
    }


}
