package com.migrou.interfaces.contaCorrente;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.migrou.types.entity.ContaCorrenteEntity;
import org.springframework.data.jpa.repository.Query;

public interface ContaCorrenteJPARepository extends JpaRepository<ContaCorrenteEntity,Integer> {
	
	List<ContaCorrenteEntity> findAllByClienteIdPessoaAndVendedorIdPessoaAndFlgResgatadoFalse(UUID idCliente, UUID idVendedor);

	List<ContaCorrenteEntity> findAllByVendedorIdPessoaAndFlgResgatadoIsTrueAndValorCashBackIsNotNull(UUID idVendedor);

	@Query("SELECT u FROM ContaCorrenteEntity u WHERE u.idCliente = ?1 and u.idVendedor = ?2 and u.valorCashBack is not null and u.flgResgatado = false")
	List<ContaCorrenteEntity> findAllCashBackNotWithdrawed(UUID idCliente, UUID idVendedor);


}
