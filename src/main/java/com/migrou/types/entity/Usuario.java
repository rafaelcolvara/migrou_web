package com.migrou.types.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Setter
@Getter
@Table(name = "USUARIO")
@EqualsAndHashCode(of = {"username"})

public class Usuario implements  UserDetails {

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Perfil> perfis =  new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    @JsonIgnore
    private ClienteEntity cliente;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    @JsonIgnore
    private VendedorEntity vendedor;

    @Id
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfis;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
