package com.migrou.interfaces.cliente;

import java.util.List;
import java.util.UUID;

import com.migrou.types.dto.PessoaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.migrou.types.entity.ClienteEntity;
import com.migrou.types.entity.PessoaEntity;
import org.springframework.data.repository.query.Param;

public interface ClienteJPARepository extends JpaRepository<ClienteEntity, UUID>{

	@Query("SELECT p FROM ClienteEntity p WHERE p.idPessoa = ?1")	
	ClienteEntity findByIdPessoa(UUID idPessoa);
	
	@Query("SELECT p FROM ClienteEntity p ")
	List<ClienteEntity> findAll();

	@Query("SELECT p FROM ClienteEntity p where p.email = :email and p.senha = :senha")
	ClienteEntity findbyEmailIgnoreCaseAndSenha(@Param("email") String email, @Param("senha") String senha);

	@Query("SELECT p FROM ClienteEntity p where p.email = ?1 ")
	ClienteEntity findbyEmailIgnoreCase(String email);


}
