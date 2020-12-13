package com.migrou.types.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class PerfilDTO implements GrantedAuthority {


    private static final long serialVersionUID = 2885330674790915808L;
    private Long id;
    private String nome;


    @Override
    public String getAuthority() {
        return nome;
    }
}
