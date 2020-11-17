package com.migrou.interfaces.cliente;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.migrou.types.entity.ClienteEntity;
import com.migrou.types.entity.PessoaEntity;

public interface ClienteJPARepository extends JpaRepository<ClienteEntity, UUID>{

	@Query("SELECT p FROM ClienteEntity p WHERE p.idPessoa = ?1")	
	ClienteEntity findByIdPessoa(UUID idPessoa);
	
	@Query("SELECT p FROM ClienteEntity p ")
	List<ClienteEntity> findAll();
	
	

}
