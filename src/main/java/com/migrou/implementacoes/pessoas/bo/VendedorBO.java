package com.migrou.implementacoes.pessoas.bo;

import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.dto.VendedorDTO;
import com.migrou.types.entity.Usuario;
import com.migrou.types.entity.VendedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VendedorBO {


	public VendedorEntity parseDTOtoPojo(VendedorDTO vendedorDTO) {

		VendedorEntity vendedor = new VendedorEntity();
		vendedor.setNomeSegmento(vendedorDTO.getNomeSegmento());
		vendedor.setNomeNegocio(vendedorDTO.getNomeNegocio());
		vendedor.setCpfCnpj(vendedorDTO.getCpfCnpj());
		vendedor.setDtCadastro(vendedorDTO.getDataCadastro());
		vendedor.setDtNascimento(vendedorDTO.getDataNascimento());
		vendedor.setUsername(vendedorDTO.getUsername());
		vendedor.setNome(vendedorDTO.getNome());
		vendedor.setUrlFoto(vendedor.getUrlFoto());

		return vendedor;

	}
	
	public VendedorDTO parsePojoToDTO(VendedorEntity vendedorEntity) {
		
		VendedorDTO dto = new VendedorDTO();
		dto.setNomeNegocio(vendedorEntity.getNomeNegocio());
		dto.setNomeSegmento(vendedorEntity.getNomeSegmento());
		dto.setUsername (vendedorEntity.getUsername());
		dto.setCpfCnpj(vendedorEntity.getCpfCnpj());
		dto.setDataCadastro(vendedorEntity.getDtCadastro());
		dto.setUrlFoto(vendedorEntity.getUrlFoto());
		dto.setNome(vendedorEntity.getNome());
		dto.setDataNascimento(vendedorEntity.getDtNascimento());
		dto.setNrCelular(vendedorEntity.getNrCelular());

		return dto;
	}

	public VendedorDTO parsePessoaDTOToVendedorDTO(PessoaDTO pessoaDTO) {
		VendedorDTO vendedorDTO = new VendedorDTO();
		vendedorDTO.setUsername(pessoaDTO.getEmail());
		vendedorDTO.setNrCelular(pessoaDTO.getNrCelular());
		vendedorDTO.setNome(pessoaDTO.getNome());
		vendedorDTO.setUrlFoto(pessoaDTO.getUrlFoto());
		vendedorDTO.setDataCadastro(pessoaDTO.getDataCadastro());
		vendedorDTO.setDataNascimento(pessoaDTO.getDataNascimento());
		vendedorDTO.setNomeNegocio(pessoaDTO.getNomeNegocio());
		vendedorDTO.setNomeSegmento(pessoaDTO.getSegmentoComercial());
		vendedorDTO.setTipoPessoa(pessoaDTO.getTipoPessoa());
		return vendedorDTO;
	}
	public PessoaDTO parseVendedorDTOToPessoaDTO(VendedorDTO  vendedorDTO) {
		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setTipoPessoa("VENDEDOR");
		pessoaDTO.setEmail(vendedorDTO.getUsername());
		pessoaDTO.setNrCelular(vendedorDTO.getNrCelular());
		pessoaDTO.setNome(vendedorDTO.getNome());
		pessoaDTO.setCpfCnpj(vendedorDTO.getCpfCnpj());
		pessoaDTO.setDataCadastro(vendedorDTO.getDataCadastro());
		pessoaDTO.setDataNascimento(vendedorDTO.getDataNascimento());
		pessoaDTO.setSegmentoComercial(vendedorDTO.getSegmentoComercial());
		pessoaDTO.setNomeNegocio(vendedorDTO.getNomeNegocio());
		return pessoaDTO;
	}



}
