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
            pessoaDTO.setTipoPessoa("VENDEDOR");
            pessoaDTO.setEmail(usuario.getUsername());
            pessoaDTO.setNrCelular(usuario.getVendedor().getNrCelular());
            pessoaDTO.setCpfCnpj(usuario.getVendedor().getCpfCnpj());
            pessoaDTO.setUrlFoto(usuario.getVendedor().getUrlFoto());
            pessoaDTO.setSegmentoComercial(vendedor.getNomeSegmento());
            pessoaDTO.setNomeNegocio(vendedor.getNomeNegocio());
        }
        if(usuario.isCliente() && Objects.nonNull(cliente)){
            pessoaDTO.setTipoPessoa("CLIENTE");
            pessoaDTO.setNome(cliente.getNome());
            pessoaDTO.setDataNascimento(cliente.getDtNascimento());
            pessoaDTO.setDataCadastro(cliente.getDtCadastro());
            pessoaDTO.setUrlFoto(cliente.getUrlFoto());
            pessoaDTO.setNrCelular(cliente.getNrCelular());
            pessoaDTO.setEmail(cliente.getUsername());
        }
        return pessoaDTO;

    }
}
