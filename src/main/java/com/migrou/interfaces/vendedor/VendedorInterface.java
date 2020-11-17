package com.migrou.interfaces.vendedor;

import java.util.List;
import java.util.UUID;

import com.migrou.types.dto.*;

public interface VendedorInterface {
	
	VendedorDTO incluiVendedor(VendedorDTO vendedorDTO) throws Exception;
	void atribuirClienteAoVendedor(ClienteVendedorDTO clienteVendedorDTO) throws Exception;
	List<VendedorDTO> consultaVendedorPorSegmento(String segmento);
	List<VendedorDTO> consutaVendedorPorNome(String nome);
	List<VendedorDTO> consutaVendedorPorNomeNegocio(String nomeNegocio);
	List<VendedorDTO> buscaTodos();
	VendedorDTO consultaVendedorPorId(UUID idVendedor) throws Exception;
	
}
