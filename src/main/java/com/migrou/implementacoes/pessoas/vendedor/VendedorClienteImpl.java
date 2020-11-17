package com.migrou.implementacoes.pessoas.vendedor;

import com.migrou.implementacoes.pessoas.bo.ClienteBO;
import com.migrou.implementacoes.pessoas.bo.VendedorBO;
import com.migrou.interfaces.cliente.ClienteJPARepository;
import com.migrou.interfaces.vendedor.VendedorJPARepository;
import com.migrou.interfaces.vendedorCliente.VendedorClienteInterface;
import com.migrou.interfaces.vendedorCliente.VendedorClienteJPARepository;
import com.migrou.types.dto.ClienteDTO;
import com.migrou.types.dto.ClienteListaVendedoresDTO;
import com.migrou.types.dto.VendedorDTO;
import com.migrou.types.dto.VendedorListaClientesDTO;
import com.migrou.types.entity.VendedorClienteEntity;
import com.migrou.types.entity.VendedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service("VendedorCliente")
public class VendedorClienteImpl implements VendedorClienteInterface {

    @Autowired
    VendedorClienteJPARepository vendedorClienteJPARepository;

    @Autowired
    VendedorJPARepository vendedorJPARepository;

    @Autowired
    ClienteJPARepository clienteJPARepository;

    @Autowired
    VendedorBO vendedorBO;

    @Autowired
    ClienteBO clienteBO;

    @Override
    public VendedorListaClientesDTO buscaClientesDoVendedor(UUID idVendedor) throws Exception {
        VendedorListaClientesDTO vendedorListaClientesDTO = new VendedorListaClientesDTO();

        List<ClienteDTO> clienteDTOS = new ArrayList<>();
        List<VendedorClienteEntity> lista = vendedorClienteJPARepository.findAllByIdVendedor(idVendedor);
        if (Objects.isNull(lista) || lista.isEmpty()){
            throw new Exception("Nao existe nenhum cliente deste vendedor");
        }
        List<ClienteDTO> newCliente = new ArrayList<>();
        vendedorListaClientesDTO.setVendedor(vendedorBO.parsePojoToDTO(vendedorJPARepository.findByIdPessoa(idVendedor).orElseThrow(()-> new Exception("Vendedor não encontrado"))));
        lista.stream().forEach(x-> {
            newCliente.add(clienteBO.parsePojoToDTO(clienteJPARepository.findByIdPessoa(x.getIdCliente())));
        });
        vendedorListaClientesDTO.setClientes(newCliente);
        return vendedorListaClientesDTO;
    }

    @Override
    public ClienteListaVendedoresDTO buscaVendedoresDoCliente(UUID idCliente) throws Exception {
        ClienteListaVendedoresDTO clienteListaVendedoresDTO = new ClienteListaVendedoresDTO();

        List<VendedorClienteEntity> lista = vendedorClienteJPARepository.findAllByIdCliente(idCliente);
        if (Objects.isNull(lista) || lista.isEmpty()){
            throw new Exception("Nao existe nenhum cliente deste vendedor");
        }
        List<VendedorDTO> newVendedorLista = new ArrayList<>();
        lista.stream().forEach(x-> {
            VendedorEntity consulta = null;
            try {
                consulta = vendedorJPARepository.findByIdPessoa(x.getIdVendedor()).orElseThrow(()-> new Exception("Vendedor Não encontrado"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            newVendedorLista.add(vendedorBO.parsePojoToDTO(consulta));
        });
        clienteListaVendedoresDTO.setVendedores(newVendedorLista);
        return clienteListaVendedoresDTO;
    }
}
