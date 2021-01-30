package com.migrou.implementacoes.pessoas.cliente;

import java.util.*;

import javax.transaction.Transactional;

import com.migrou.interfaces.cliente.ClienteInterface;
import com.migrou.interfaces.usuario.UsuarioInterface;
import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migrou.implementacoes.pessoas.bo.ClienteBO;
import com.migrou.interfaces.campanha.CampanhaJPARepository;
import com.migrou.interfaces.cliente.ClienteJPARepository;
import com.migrou.types.dto.ClienteDTO;
import com.migrou.types.entity.CampanhaEntity;

@Service("ClienteService")
public class ClienteImpl implements ClienteInterface {

	@Autowired
	ClienteJPARepository clienteJpaRepository;

	@Autowired
	ClienteBO clienteBO;
	
	@Autowired
	CampanhaJPARepository campanhaJPA;

	@Autowired
	UsuarioInterface usuarioInterface;


	@Override
	public ClienteEntity atribuirCampanha(ClienteDTO clienteDTO) throws Exception {

		CampanhaEntity campanha = campanhaJPA.findByIdCampanha(clienteDTO.getIdCampanha());
		if (campanha == null) throw new Exception("Campanha não existe");
		ClienteEntity cliente = clienteJpaRepository.findByUsername(clienteDTO.getUsername());
		if (cliente==null) throw new Exception("Cliente não existe");
		cliente.setCampanha(campanha);
		return clienteJpaRepository.save(cliente);		
	}

	@Override
	@Transactional
	public ClienteDTO incluirCliente(ClienteDTO clienteDTO) throws Exception {

		ClienteEntity cliente = clienteJpaRepository.findByUsername(clienteDTO.getUsername());
		if (!Objects.isNull(cliente)) {
			throw new Exception("Cliente já cadastrado com este email");
		} else		{
			cliente = new ClienteEntity();
		}

		PessoaDTO pessoaDTO = new PessoaDTO();
		pessoaDTO.setNome(clienteDTO.getNome());
		pessoaDTO.setTipoPessoa("CLIENTE");
		pessoaDTO.setCpfCnpj(clienteDTO.getCpfCnpj());
		pessoaDTO.setDataCadastro(new Date());
		pessoaDTO.setNrCelular(clienteDTO.getNrCelular());
		pessoaDTO.setEmail(clienteDTO.getUsername());
		pessoaDTO.setSenha(clienteDTO.getPassword());
		usuarioInterface.salva(pessoaDTO);

		cliente.setUsername(clienteDTO.getUsername());
		cliente.setCpfCnpj(clienteDTO.getCpfCnpj());
		cliente.setNome(clienteDTO.getNome());
		cliente.setDtCadastro(new Date());


		return clienteBO.parsePojoToDTO(clienteJpaRepository.save(cliente));
		
	}
	
	
	@Override
	public ClienteDTO consultaClienteporID(String username) {

		ClienteEntity clienteEntity =  clienteJpaRepository.findByUsername(username);
		return new ClienteBO().parsePojoToDTO(clienteEntity);
	}
	
	@Override
	public List<ClienteDTO> buscaTodos() {
		List<ClienteDTO> dtos = new ArrayList<>();
		
		clienteJpaRepository.findAll().stream().forEach((c) -> {
			dtos.add(clienteBO.parsePojoToDTO(c));
		});
		return dtos;
	}


}
