package com.migrou.implementacoes.pessoas.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.migrou.implementacoes.pessoas.bo.PessoaBO;
import com.migrou.types.dto.ClienteDTO;
import com.migrou.types.entity.CampanhaEntity;
import com.migrou.types.entity.ClienteEntity;

@Component
public class ClienteBO {
	
	@Autowired
	PessoaBO pessoaBo;
	
	public ClienteEntity parseDTOtoPojo(ClienteDTO dto) {
		ClienteEntity cliente = new ClienteEntity();
		CampanhaEntity campanha = new CampanhaEntity();
		campanha.setIdCampanha(dto.getIdCampanha());
		cliente.setCampanha(campanha);
		cliente.setIdPessoa(dto.getIdCliente());
		cliente.setCpfCnpj(dto.getPessoaDTO().getCpfCnpj());
		cliente.setEmail(dto.getPessoaDTO().getEmail());
		cliente.setDtNascimento(dto.getPessoaDTO().getDataNascimento());
		return cliente;
	}

	public ClienteDTO parsePojoToDTO(ClienteEntity entity) {
		ClienteDTO dto = new ClienteDTO();
		if (entity.getCampanha()!=null) {
			dto.setIdCampanha(entity.getCampanha().getIdCampanha());
		}
		dto.setIdCliente(entity.getIdPessoa());
		dto.setPessoaDTO(pessoaBo.parsePojoToDto(entity));

		return dto;
		
	}
	
}
