package com.migrou.interfaces.vendedorCliente;

import com.migrou.types.dto.ClienteListaVendedoresDTO;
import com.migrou.types.dto.VendedorListaClientesDTO;

public interface VendedorClienteInterface {

    VendedorListaClientesDTO buscaClientesDoVendedor(String usernameVendedor) throws Exception;

    ClienteListaVendedoresDTO buscaVendedoresDoCliente(String usernameCliente) throws Exception;
}
