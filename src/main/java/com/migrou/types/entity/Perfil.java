package com.migrou.types.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERFIL")
@Data
public class Perfil implements GrantedAuthority {

    @Id
    private Long id;

    @Column
    private String nome;

    @Override
    public String getAuthority() {
        return nome;
    }
}
