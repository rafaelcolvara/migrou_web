package com.migrou.controller.vendedor;

import com.migrou.implementacoes.pessoas.vendedor.VendedorClienteImpl;
import com.migrou.implementacoes.pessoas.vendedor.VendedorImpl;
import com.migrou.interfaces.vendedor.VendedorInterface;
import com.migrou.types.dto.ClienteListaVendedoresDTO;
import com.migrou.types.dto.ClienteVendedorDTO;
import com.migrou.types.dto.VendedorDTO;
import com.migrou.types.dto.VendedorListaClientesDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Api(value = "CRUD vendedores")
@CrossOrigin(origins = "*")

public class VendedorController {


	@Autowired
	VendedorInterface vendedorservice;

	@Autowired
	VendedorClienteImpl vendedorCliente;
	
	@PostMapping(value = "/vendedor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Cadastra Vendedor")
    public ResponseEntity<VendedorDTO> CadastraVendedor(@RequestBody final VendedorDTO vendedorDTO) {
	
		try {
			return new ResponseEntity<VendedorDTO>(vendedorservice.incluiVendedor(vendedorDTO), HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		}		
	}

	@PatchMapping(value = "/vendedor/vinculaCliente", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Vincula um cliente ao vendedor")
    public ResponseEntity<ClienteVendedorDTO> VinculacaoVendedorCliente(@RequestBody final ClienteVendedorDTO vendedorClienteDTO) {
		
		try {
			vendedorservice.atribuirClienteAoVendedor(vendedorClienteDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ClienteVendedorDTO>(vendedorClienteDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/vendedor/buscaTodos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consulta todos os Vendedores")
    public ResponseEntity<List<VendedorDTO>> BuscaTodosVendedor() {
		
		return new ResponseEntity<List<VendedorDTO>>(vendedorservice.buscaTodos(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/vendedor/{idVendedor}/buscaClientes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consulta todos os Clientes do Vendedor fornecido")
    public ResponseEntity<VendedorListaClientesDTO> BuscaClientesDoVendedor(@PathVariable("idVendedor") String usernameVendedor ) {
		VendedorListaClientesDTO vendedorListaClientesDTO ;
		try{
			vendedorListaClientesDTO = vendedorCliente.buscaClientesDoVendedor(usernameVendedor);
		}catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<VendedorListaClientesDTO>(vendedorListaClientesDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/vendedor/cliente/{idCliente}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consulta todos os Vendedores do Cliente Fornecido")
	public ResponseEntity<ClienteListaVendedoresDTO> BuscaVendedorDoCliente(@PathVariable("idCliente") String usernameCliente ) {
		ClienteListaVendedoresDTO cliente;
		try {
			cliente = vendedorCliente.buscaVendedoresDoCliente(usernameCliente);
		}catch (Exception e){
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ClienteListaVendedoresDTO>(cliente, HttpStatus.OK);
	}

	@GetMapping(value = "/vendedor/{emailVendedor}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consulta dados do vendedor utilizando o seu login")
	public ResponseEntity<VendedorDTO> buscaVendedor(@PathVariable("emailVendedor") String usernameVendedor ) {
		VendedorDTO vendedorDTO;
		try {
			vendedorDTO = vendedorservice.consultaVendedorPorId(usernameVendedor);
		}catch (Exception e){
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<VendedorDTO>(vendedorDTO, HttpStatus.OK);
	}

}

