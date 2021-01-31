package com.migrou.implementacoes.pessoas.bo;

import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.entity.ClienteEntity;
import com.migrou.types.entity.Usuario;
import com.migrou.types.entity.VendedorEntity;

import java.util.Objects;

public class UsuarioBO {

    public static PessoaDTO parseUsuarioEntityPessoaDTO(Usuario usuario){
        PessoaDTO pessoaDTO = new PessoaDTO();
        VendedorEntity vendedor = usuario.getVendedor();
        ClienteEntity cliente = usuario.getCliente();
        if (usuario.isVendedor() && !Objects.isNull(vendedor)) {
            pessoaDTO.setSegmentoComercial(vendedor.getNomeSegmento());
            pessoaDTO.setNomeNegocio(vendedor.getNomeNegocio());
        }
        return pessoaDTO;

    }
}
