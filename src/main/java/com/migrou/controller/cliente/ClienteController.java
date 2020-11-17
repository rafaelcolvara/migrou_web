package com.migrou.controller.cliente;

import java.util.List;
import java.util.UUID;

import com.migrou.implementacoes.pessoas.vendedor.VendedorClienteImpl;
import com.migrou.types.dto.ClienteListaVendedoresDTO;
import com.migrou.types.dto.VendedorListaClientesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.migrou.implementacoes.pessoas.cliente.ClienteImpl;
import com.migrou.types.dto.ClienteDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/cliente")
@Api(value = "Api de consulta a usuarios do sistema")
@CrossOrigin(origins = "*")
public class ClienteController {

	@Autowired
	ClienteImpl cliente;

	@Autowired
	VendedorClienteImpl vendedorCliente;
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Cadastra cliente")
    public ResponseEntity<ClienteDTO> CadastraCliente(@RequestBody ClienteDTO clienteDTO) {
		try {
			return new ResponseEntity<ClienteDTO>(cliente.incluirCliente(clienteDTO), HttpStatus.OK);
		}catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping(value = "/buscaTodos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consulta Todos os clientes")
    public ResponseEntity<List<ClienteDTO>> ConsultaTodos() {
		try {
			return new ResponseEntity<List<ClienteDTO>>(cliente.buscaTodos(), HttpStatus.OK);
		}catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	@GetMapping(value = "{idCliente}/buscaSeusVendedores", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consulta Todos os Vendedores do cliente informado")
	public ResponseEntity<ClienteListaVendedoresDTO> ConsultaVendedoresDoCliente(@PathVariable UUID idCliente) {
		try {
			return new ResponseEntity<ClienteListaVendedoresDTO>(vendedorCliente.buscaVendedoresDoCliente(idCliente), HttpStatus.OK);
		}catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
}
