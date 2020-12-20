package com.migrou.interfaces.cliente;

import com.migrou.types.dto.ClienteDTO;
import com.migrou.types.entity.ClienteEntity;

import java.util.List;

public interface ClienteInterface {

	ClienteEntity atribuirCampanha(ClienteDTO clienteDTO) throws Exception;

	ClienteDTO incluirCliente(ClienteDTO clienteDTO) throws Exception ;

	ClienteDTO consultaClienteporID(String username);

	List<ClienteDTO> buscaTodos();
}
