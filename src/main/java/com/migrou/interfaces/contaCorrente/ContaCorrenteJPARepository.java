package com.migrou.interfaces.contaCorrente;

import com.migrou.types.entity.ContaCorrenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContaCorrenteJPARepository extends JpaRepository<ContaCorrenteEntity,Integer> {
	
	List<ContaCorrenteEntity> findAllByClienteUsernameAndVendedorUsernameAndFlgResgatadoFalse(String username, String idVendedor);

	List<ContaCorrenteEntity> findAllByVendedorUsernameAndFlgResgatadoIsTrueAndValorCashBackIsNotNull(String idVendedor);

	@Query("SELECT u FROM ContaCorrenteEntity u WHERE u.idCliente = ?1 and u.idVendedor = ?2 and u.valorCashBack is not null and u.flgResgatado = false")
	List<ContaCorrenteEntity> findAllCashBackNotWithdrawed(String usernameCliente, String idVendedor);


}
