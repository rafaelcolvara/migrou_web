package com.migrou.implementacoes.pessoas.cliente;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import com.migrou.types.dto.ClienteListaVendedoresDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migrou.implementacoes.pessoas.bo.ClienteBO;
import com.migrou.interfaces.campanha.CampanhaJPARepository;
import com.migrou.interfaces.cliente.ClienteJPARepository;
import com.migrou.interfaces.pessoas.ClienteInterface;
import com.migrou.interfaces.pessoas.PessoaJPARpository;
import com.migrou.types.dto.ClienteDTO;
import com.migrou.types.entity.CampanhaEntity;
import com.migrou.types.entity.ClienteEntity;

@Service("ClienteService")
public class ClienteImpl implements ClienteInterface{

	@Autowired
	ClienteJPARepository clienteJpaRepository;

	@Autowired
	PessoaJPARpository pessoaJpaRepository;

	@Autowired
	ClienteBO clienteBO;
	
	@Autowired
	CampanhaJPARepository campanhaJPA;
	
	
	
	@Override
	public ClienteEntity atribuirCampanha(UUID idCliente, Integer idCampanha) throws Exception {
		 
		CampanhaEntity campanha = campanhaJPA.findByIdCampanha(idCampanha);
		if (campanha == null) throw new Exception("Campanha não existe");
		ClienteEntity cliente = clienteJpaRepository.findByIdPessoa(idCliente);
		if (cliente==null) throw new Exception("Cliente não existe");
		cliente.setCampanha(campanha);
		return clienteJpaRepository.save(cliente);		
	}

	@Override
	@Transactional
	public ClienteDTO incluirCliente(ClienteDTO clienteDTO) throws Exception {
			
		if (validaClienteCadastradoPorEmail(clienteDTO.getPessoaDTO().getEmail())) throw new Exception("Cliente já cadastrado com este email");
		
		ClienteEntity cliente = new ClienteEntity();
		cliente.setEmail(clienteDTO.getPessoaDTO().getEmail());
		cliente.setCpfCnpj(clienteDTO.getPessoaDTO().getCpfCnpj());
		cliente.setNome(clienteDTO.getPessoaDTO().getNome());
		cliente.setDtCadastro(new Date());
		return clienteBO.parsePojoToDTO(clienteJpaRepository.save(cliente));
		
	}
	
	
	@Override
	public ClienteEntity consultaClienteporID(UUID idCliente) {
		
		return clienteJpaRepository.findByIdPessoa(idCliente);
	}
	
	@Override
	public List<ClienteDTO> buscaTodos() {
		List<ClienteDTO> dtos = new ArrayList<>();
		
		clienteJpaRepository.findAll().stream().forEach((c) -> {
			dtos.add(clienteBO.parsePojoToDTO(c));
		});
		return dtos;
	}

	private boolean validaClienteCadastradoPorEmail(String email) {
		
		return (pessoaJpaRepository.findbyEmailIgnoreCase(email)!=null);
	}


}
