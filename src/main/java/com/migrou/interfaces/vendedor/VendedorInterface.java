package com.migrou.interfaces.vendedor;

import com.migrou.types.dto.ClienteVendedorDTO;
import com.migrou.types.dto.VendedorDTO;

import java.util.List;

public interface VendedorInterface {
	
	VendedorDTO incluiVendedor(VendedorDTO vendedorDTO) throws Exception;
	void atribuirClienteAoVendedor(ClienteVendedorDTO clienteVendedorDTO) throws Exception;
	List<VendedorDTO> consultaVendedorPorSegmento(String segmento);
	List<VendedorDTO> consutaVendedorPorNome(String nome);
	List<VendedorDTO> consutaVendedorPorNomeNegocio(String nomeNegocio);
	List<VendedorDTO> buscaTodos();
	VendedorDTO consultaVendedorPorId(String usernameVendedor) throws Exception;
	
}
