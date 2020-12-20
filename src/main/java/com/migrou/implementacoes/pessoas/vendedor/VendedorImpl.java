package com.migrou.implementacoes.pessoas.vendedor;

import com.migrou.implementacoes.pessoas.bo.ClienteBO;
import com.migrou.implementacoes.pessoas.bo.VendedorBO;
import com.migrou.interfaces.cliente.ClienteJPARepository;
import com.migrou.interfaces.contaCorrente.ContaCorrenteJPARepository;
import com.migrou.interfaces.usuario.UsuarioJPA;
import com.migrou.interfaces.vendedor.VendedorInterface;
import com.migrou.interfaces.vendedor.VendedorJPARepository;
import com.migrou.interfaces.vendedorCliente.VendedorClienteJPARepository;
import com.migrou.types.dto.ClienteVendedorDTO;
import com.migrou.types.dto.VendedorDTO;
import com.migrou.types.entity.Usuario;
import com.migrou.types.entity.VendedorClienteEntity;
import com.migrou.types.entity.VendedorClientePK;
import com.migrou.types.entity.VendedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("VendedorService")
public class VendedorImpl implements VendedorInterface {

	@Autowired
	VendedorJPARepository vendedorJPARepository;

	@Autowired
	VendedorClienteJPARepository vendedorClienteJPARepository;

	@Autowired
	ClienteJPARepository clienteJPARepository;

	@Autowired
	ContaCorrenteJPARepository contaCorrenteJPA;
	
	@Autowired
	VendedorBO vendedorBO;
	
	@Autowired
	ClienteBO clienteBO;

	@Autowired
	UsuarioJPA usuarioJPA;

	@Override
	@Transactional
	public VendedorDTO incluiVendedor(VendedorDTO vendedorDTO) throws Exception {
		
		if (vendedorJPARepository.findByUsername(vendedorDTO.getUsername()).isPresent())
			throw new Exception("Já existe outra pessoa com este email");
		VendedorEntity vendedorEntity = vendedorBO.parseDTOtoPojo(vendedorDTO);
		Usuario usuario = usuarioJPA.findByUsername(vendedorDTO.getUsername()).get();
		vendedorEntity.setUsuario(usuario);
		return vendedorBO.parsePojoToDTO(vendedorJPARepository.saveAndFlush(vendedorEntity));
	}

	
	@Override
	public List<VendedorDTO> buscaTodos() {
		List<VendedorDTO> resultDto = new ArrayList<>();
		vendedorJPARepository.findAll().stream().forEach((vendedor) -> {
			resultDto.add(vendedorBO.parsePojoToDTO(vendedor));			
		});
		 
		return resultDto;
	}


	@Override
	public List<VendedorDTO> consultaVendedorPorSegmento(String segmento) {
		
		List<VendedorDTO> resultDto = new ArrayList<>();
		vendedorJPARepository.findByNomeSegmento(segmento).stream().forEach((vendedor) -> {
			resultDto.add(vendedorBO.parsePojoToDTO(vendedor));			
		});
		 
		return resultDto;
	}

	@Override
	public List<VendedorDTO> consutaVendedorPorNome(String nome) {
		List<VendedorDTO> resultDto = new ArrayList<>();
		vendedorJPARepository.findByNomeContains(nome).stream().forEach((vendedor) -> {
			resultDto.add(vendedorBO.parsePojoToDTO(vendedor));			
		});
		 
		return resultDto;
	}

	@Override
	public List<VendedorDTO> consutaVendedorPorNomeNegocio(String nomeNegocio) {
		List<VendedorDTO> resultDto = new ArrayList<>();
		vendedorJPARepository.findByNomeNegocio(nomeNegocio).stream().forEach((vendedor) -> {
			resultDto.add(vendedorBO.parsePojoToDTO(vendedor));			
		});
		 
		return resultDto;
	}

	@Override
	public VendedorDTO consultaVendedorPorId(String usernameVendedor) throws Exception {
		 
		return vendedorBO.parsePojoToDTO(vendedorJPARepository.findByUsername(usernameVendedor).orElseThrow(()-> new Exception("Vendedor não encontrado")));
	}

	@Override
	@Transactional
	public void atribuirClienteAoVendedor(ClienteVendedorDTO clienteVendedorDTO) throws Exception {

		VendedorClientePK vendedorClientePK =  new VendedorClientePK();
		VendedorClienteEntity vendedorClienteEntity = new VendedorClienteEntity();
		if (Objects.isNull(clienteVendedorDTO.getVendedor()) || Objects.isNull(clienteVendedorDTO.getVendedor().getUsername()) ) {
			throw new Exception("É necessário informar o Vendedor");
		}
		if (Objects.isNull(clienteVendedorDTO.getCliente()) || Objects.isNull(clienteVendedorDTO.getCliente().getUsername())) {
			throw new Exception("É necessário informar o Cliente");
		}

		vendedorClientePK.setIdCliente(clienteJPARepository.findById(clienteVendedorDTO.getCliente().getUsername()).orElseThrow(() -> new Exception("Cliente nao encontrado")).getUsername());
		vendedorClientePK.setIdVendedor(vendedorJPARepository.findById(clienteVendedorDTO.getVendedor().getUsername()).orElseThrow(()-> new Exception("Vendedor nao encontrado")).getUsername());

		if (vendedorClienteJPARepository.findById(vendedorClientePK).isPresent()){
			throw new Exception("Cliente já atribuido ao vendedor");
		}

		vendedorClienteEntity.setIdCliente(vendedorClientePK.getIdCliente());
		vendedorClienteEntity.setIdVendedor(vendedorClientePK.getIdVendedor());
		try {
			vendedorClienteJPARepository.save(vendedorClienteEntity);
		}catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
}
