package com.migrou.interfaces.contaCorrente;

import com.migrou.types.dto.ClienteDashDTO;
import com.migrou.types.dto.ClienteVendedorDTO;
import com.migrou.types.dto.ContaCorrenteDTO;
import com.migrou.types.dto.UltimoResgateDTO;
import com.migrou.types.entity.ContaCorrenteEntity;

import java.util.List;
import java.util.UUID;

public interface ContaCorrenteInterface {


	ContaCorrenteDTO realizaLancamento(ContaCorrenteDTO contacorrente) throws Exception;
	ContaCorrenteDTO realizaResgate(UUID idCliente, UUID idVendedor) throws Exception;
	ContaCorrenteDTO geraValorCashBack(ContaCorrenteDTO contaCorrenteDTO) throws Exception;
	List<ContaCorrenteEntity> buscaLancamentosNaoResgatados(UUID idCliente, UUID idVendedor);
	List<UltimoResgateDTO> buscaUltimoResgateDosClientes(UUID idVendedor);
	ClienteDashDTO buscaDashCliente(UUID idCliente, UUID idVendedor) throws  Exception;
	void validaLancamento(UUID idCliente, UUID idVendedor) throws Exception;
	ContaCorrenteEntity buscaCashBack(UUID idCliente, UUID idVendedor);

	
}
