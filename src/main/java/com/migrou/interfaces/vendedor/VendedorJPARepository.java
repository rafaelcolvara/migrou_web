package com.migrou.interfaces.vendedor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.migrou.types.entity.ClienteEntity;
import com.migrou.types.entity.VendedorEntity;
import org.springframework.data.jpa.repository.Query;

public interface VendedorJPARepository extends JpaRepository<VendedorEntity, UUID> {
	
	List<VendedorEntity> findByNomeContains(String nome);
	List<VendedorEntity> findByNomeSegmento(String nomeSegmento);
	List<VendedorEntity> findByNomeNegocio(String nomeNegocio);
	List<VendedorEntity> findByEmail(String email);
	List<VendedorEntity> findAll();
	Optional<VendedorEntity> findByIdPessoa(UUID idPessoa);

	@Query("SELECT p FROM VendedorEntity p where p.email = :email and p.senha = :senha")
	VendedorEntity findbyEmailIgnoreCaseAndSenha(String email, String senha);

	@Query("SELECT p FROM VendedorEntity p where p.email = :email ")
	VendedorEntity findbyEmailIgnoreCase(String email);



}
