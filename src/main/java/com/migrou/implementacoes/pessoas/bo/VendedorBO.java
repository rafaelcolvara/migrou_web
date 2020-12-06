package com.migrou.implementacoes.pessoas.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.migrou.types.dto.VendedorDTO;
import com.migrou.types.entity.VendedorEntity;

import java.util.UUID;

@Component
public class VendedorBO {

	@Autowired
	PessoaBO pessoaBo;
	
	public VendedorEntity parseDTOtoPojo(VendedorDTO vendedorDTO) {

		VendedorEntity vendedor = new VendedorEntity();
		vendedor.setNomeSegmento(vendedorDTO.getNomeSegmento());
		vendedor.setNomeNegocio(vendedorDTO.getNomeNegocio());
		if (vendedorDTO.getPessoaDTO() != null) {
			vendedor.setCpfCnpj(vendedorDTO.getPessoaDTO().getCpfCnpj());
			vendedor.setDtCadastro(vendedorDTO.getPessoaDTO().getDataCadastro());
			vendedor.setDtNascimento(vendedorDTO.getPessoaDTO().getDataNascimento());
			vendedor.setEmail(vendedorDTO.getPessoaDTO().getEmail());
			vendedor.setNome(vendedorDTO.getPessoaDTO().getNome());
			if (vendedorDTO.getPessoaDTO().getId()!=null) vendedor.setIdPessoa(vendedorDTO.getPessoaDTO().getId());
		}
		return vendedor;

	}
	
	public VendedorDTO parsePojoToDTO(VendedorEntity vendedorEntity) {
		
		VendedorDTO dto = new VendedorDTO();
		dto.setNomeNegocio(vendedorEntity.getNomeNegocio());
		dto.setNomeSegmento(vendedorEntity.getNomeSegmento());
		dto.setIdVendedor(vendedorEntity.getIdPessoa());
		dto.setPessoaDTO(pessoaBo.parsePojoToDto(vendedorEntity));
		return dto;
	}

}
