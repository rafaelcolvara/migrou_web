package com.migrou.config;

import com.migrou.implementacoes.auth.TokenService;
import com.migrou.interfaces.pessoas.PessoaInterface;
import com.migrou.interfaces.usuario.UsuarioJPA;
import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.entity.Usuario;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.security.x509.OtherName;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;

    private UsuarioJPA usuarioJPA;

    public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioJPA usuarioJPA){
        this.tokenService = tokenService;
        this.usuarioJPA = usuarioJPA;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);

        boolean valido = tokenService.isTokenValido(token);
        if (valido) {
            autenticarCliente(token);
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {

        String username = tokenService.getUsernameToken(token);
        Usuario usuario = usuarioJPA.findByUsername(username).get();
        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(usuario.getUsername(), null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token==null || token.isEmpty() || !token.startsWith("Bearer")){
            return null;
        }
        return token.substring(7, token.length());

    }
}
