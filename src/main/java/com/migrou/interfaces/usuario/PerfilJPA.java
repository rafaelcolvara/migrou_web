package com.migrou.interfaces.usuario;

import com.migrou.types.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilJPA extends JpaRepository<Perfil, Long> {
}
