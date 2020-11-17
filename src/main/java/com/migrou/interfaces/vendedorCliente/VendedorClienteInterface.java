package com.migrou.interfaces.vendedorCliente;

import com.migrou.types.dto.ClienteListaVendedoresDTO;
import com.migrou.types.dto.VendedorListaClientesDTO;

import java.util.UUID;

public interface VendedorClienteInterface {

    VendedorListaClientesDTO buscaClientesDoVendedor(UUID idVendedor) throws Exception;

    ClienteListaVendedoresDTO buscaVendedoresDoCliente(UUID idCliente) throws Exception;
}
