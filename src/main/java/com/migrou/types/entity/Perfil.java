package com.migrou.types.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERFIL")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
