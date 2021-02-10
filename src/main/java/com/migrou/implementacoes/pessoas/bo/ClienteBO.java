package com.migrou.implementacoes.pessoas.bo;

import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.migrou.types.dto.ClienteDTO;
import com.migrou.types.entity.CampanhaEntity;

@Component
public class ClienteBO {
	

	public ClienteEntity parseDTOtoPojo(ClienteDTO dto) {
		ClienteEntity cliente = new ClienteEntity();
		CampanhaEntity campanha = new CampanhaEntity();
		campanha.setIdCampanha(dto.getIdCampanha());
		cliente.setCampanha(campanha);
		cliente.setUsername(dto.getUsername());
		cliente.setCpfCnpj(dto.getCpfCnpj());
		cliente.setDtNascimento(dto.getDataNascimento());
		return cliente;
	}

	public ClienteDTO parsePojoToDTO(ClienteEntity entity) {
		ClienteDTO dto = new ClienteDTO();
		if (entity.getCampanha()!=null) {
			dto.setIdCampanha(entity.getCampanha().getIdCampanha());
		}
		dto.setUsername(entity.getUsername()) ;
		dto.setDataCadastro(entity.getDtCadastro());
		dto.setNome(entity.getNome());
		dto.setUrlFoto(entity.getUrlFoto());
		dto.setCpfCnpj(entity.getCpfCnpj());
		dto.setDataNascimento(entity.getDtNascimento());
		dto.setNrCelular(entity.getNrCelular());
		dto.setTipoPessoa("CLIENTE");

		return dto;
	}
	public PessoaDTO parseClienteDTOPessoaDTO(ClienteDTO clienteDTO) {

		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setEmail(clienteDTO.getUsername());
		pessoaDTO.setTipoPessoa("CLIENTE");
		pessoaDTO.setNome(clienteDTO.getNome());
		pessoaDTO.setCpfCnpj(clienteDTO.getCpfCnpj());
		pessoaDTO.setDataCadastro(clienteDTO.getDataCadastro());
		pessoaDTO.setDataNascimento(clienteDTO.getDataNascimento());
		pessoaDTO.setNrCelular(clienteDTO.getNrCelular());
		return pessoaDTO;
	}

	public ClienteDTO parsePessoaDTOClienteDTO(PessoaDTO pessoaDTO) {

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setUsername(pessoaDTO.getEmail());
		clienteDTO.setTipoPessoa("CLIENTE");
		clienteDTO.setNome(pessoaDTO.getNome());
		clienteDTO.setCpfCnpj(pessoaDTO.getCpfCnpj());
		clienteDTO.setDataNascimento(pessoaDTO.getDataNascimento());
		clienteDTO.setDataCadastro(pessoaDTO.getDataCadastro());
		clienteDTO.setNrCelular(pessoaDTO.getNrCelular());

		return clienteDTO;
	}
	
}
