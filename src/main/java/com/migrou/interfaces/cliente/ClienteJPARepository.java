package com.migrou.interfaces.cliente;

import com.migrou.types.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteJPARepository extends JpaRepository<ClienteEntity, String>{

	@Query("SELECT p FROM ClienteEntity p WHERE username = ?1")
	ClienteEntity findByUsername(String username);
	
	@Query("SELECT p FROM ClienteEntity p ")
	List<ClienteEntity> findAll();

}
