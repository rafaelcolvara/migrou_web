package com.migrou.interfaces.contaCorrente;

import com.migrou.types.dto.ClienteDashDTO;
import com.migrou.types.dto.ContaCorrenteDTO;
import com.migrou.types.dto.UltimoResgateDTO;
import com.migrou.types.entity.ContaCorrenteEntity;

import java.util.List;

public interface ContaCorrenteInterface {


	ContaCorrenteDTO realizaLancamento(ContaCorrenteDTO contacorrente) throws Exception;
	ContaCorrenteDTO realizaResgate(String usernameCliente, String usernameVendedor) throws Exception;
	ContaCorrenteDTO geraValorCashBack(ContaCorrenteDTO contaCorrenteDTO) throws Exception;
	List<ContaCorrenteEntity> buscaLancamentosNaoResgatados(String usernameCliente, String usernameVendedor);
	List<UltimoResgateDTO> buscaUltimoResgateDosClientes(String usernameVendedor);
	ClienteDashDTO buscaDashCliente(String usernameCliente, String usernameVendedor) throws  Exception;
	void validaLancamento(String usernameCliente, String usernameVendedor) throws Exception;
	ContaCorrenteEntity buscaCashBack(String usernameCliente, String usernameVendedor);

	
}
