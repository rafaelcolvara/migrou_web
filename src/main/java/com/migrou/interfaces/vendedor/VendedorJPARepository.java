package com.migrou.interfaces.vendedor;

import com.migrou.types.entity.VendedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VendedorJPARepository extends JpaRepository<VendedorEntity, String> {
	
	List<VendedorEntity> findByNomeContains(String nome);
	List<VendedorEntity> findByNomeSegmento(String nomeSegmento);
	List<VendedorEntity> findByNomeNegocio(String nomeNegocio);
	List<VendedorEntity> findAll();
	Optional<VendedorEntity> findByUsername(String usernameVendedor);

	@Query("SELECT p FROM VendedorEntity p where p.username = :email ")
	VendedorEntity findbyEmailIgnoreCase(@Param("email") String email);


}
