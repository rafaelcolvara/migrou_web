package com.migrou.interfaces.usuario;

import com.migrou.types.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioJPA extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

}
