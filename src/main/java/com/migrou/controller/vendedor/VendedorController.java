package com.migrou.controller.vendedor;

import com.migrou.implementacoes.pessoas.vendedor.VendedorClienteImpl;
import com.migrou.implementacoes.pessoas.vendedor.VendedorImpl;
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
import java.util.UUID;

@RestController
@RequestMapping(value = "/vendedor")
@Api(value = "Api de consulta a usuarios do sistema")
@CrossOrigin(origins = "*")

public class VendedorController {


	@Autowired
	VendedorImpl vendedorservice;

	@Autowired
	VendedorClienteImpl vendedorCliente;
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Cadastra Vendedor")
    public ResponseEntity<VendedorDTO> CadastraVendedor(@RequestBody final VendedorDTO vendedorDTO) {
	
		try {
			return new ResponseEntity<VendedorDTO>(vendedorservice.incluiVendedor(vendedorDTO), HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		}		
	}

	@PatchMapping(value = "/vinculaCliente", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@GetMapping(value = "/buscaTodos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consulta todos os Vendedores")
    public ResponseEntity<List<VendedorDTO>> BuscaTodosVendedor() {
		
		return new ResponseEntity<List<VendedorDTO>>(vendedorservice.buscaTodos(), HttpStatus.OK);
	}
	
	@GetMapping(value = "{idVendedor}/buscaClientes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consulta todos os Clientes do Vendedor fornecido")
    public ResponseEntity<VendedorListaClientesDTO> BuscaClientesDoVendedor(@PathVariable("idVendedor") UUID idVendedor ) {
		VendedorListaClientesDTO vendedorListaClientesDTO ;
		try{
			vendedorListaClientesDTO = vendedorCliente.buscaClientesDoVendedor(idVendedor);
		}catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<VendedorListaClientesDTO>(vendedorListaClientesDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/cliente/{idCliente}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consulta todos os Vendedores do Cliente Fornecido")
	public ResponseEntity<ClienteListaVendedoresDTO> BuscaVendedorDoCliente(@PathVariable("idCliente") UUID idCliente ) {
		ClienteListaVendedoresDTO cliente;
		try {
			cliente = vendedorCliente.buscaVendedoresDoCliente(idCliente);
		}catch (Exception e){
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ClienteListaVendedoresDTO>(cliente, HttpStatus.OK);
	}

}

