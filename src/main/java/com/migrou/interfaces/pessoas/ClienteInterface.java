package com.migrou.interfaces.pessoas;

import java.util.List;
import java.util.UUID;

import com.migrou.types.dto.ClienteDTO;
import com.migrou.types.dto.ClienteListaVendedoresDTO;
import com.migrou.types.entity.ClienteEntity;
import com.migrou.types.entity.PessoaEntity;

public interface ClienteInterface {
	
	ClienteEntity atribuirCampanha(UUID idPessoa, Integer idCampanha) throws Exception;
	ClienteDTO incluirCliente(ClienteDTO clienteDTO) throws Exception;
	PessoaEntity consultaClienteporID(UUID idCliente);
	List<ClienteDTO> buscaTodos();

}
