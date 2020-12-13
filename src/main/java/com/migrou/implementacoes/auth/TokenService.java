package com.migrou.implementacoes.auth;

import com.migrou.types.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.migrou.config.SecurityConstants.SECRET;
import static com.migrou.config.SecurityConstants.TOKEN_PREFIX;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiracao}")
    private String tempoExpiracao;


    public String gerarToken(Authentication authentication){
        Usuario userLogado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date expiracao = new Date(hoje.getTime() + Long.parseLong(tempoExpiracao));
        return TOKEN_PREFIX + Jwts.builder().setIssuer("API do Login")
                .setSubject(userLogado.getUsername())
                .setIssuedAt(hoje)
                .setExpiration(expiracao)
                .signWith(SignatureAlgorithm.HS512, secret )
                .compact();

    }

    public boolean isTokenValido(String token){

        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String getUsernameToken(String token) {

        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return body.getSubject();
    }
}
