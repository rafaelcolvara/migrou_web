package com.migrou.controller.contaCorrente;

import com.migrou.implementacoes.contaCorrente.ContaCorrenteImpl;
import com.migrou.implementacoes.pessoas.vendedor.VendedorClienteImpl;
import com.migrou.types.dto.ClienteDashDTO;
import com.migrou.types.dto.ContaCorrenteDTO;
import com.migrou.types.dto.UltimoResgateDTO;
import com.migrou.types.dto.VendedorListaClientesDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/contaCorrente")
@Api(value = "Api de administração de lançamentos em conta corrente")
@CrossOrigin(origins = "*")
public class ContaCorrenteController {

    @Autowired
    ContaCorrenteImpl contacorrenteService;

    @Autowired
    VendedorClienteImpl vendedorCliente;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registra lançamento na conta corrente")
    public ResponseEntity<?> RegistraLancamentoContaCorrente(@RequestBody final ContaCorrenteDTO cc) {

        try {
            return new ResponseEntity<ContaCorrenteDTO>(contacorrenteService.realizaLancamento(cc), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "resgate/vendedor/{idVendedor}/cliente/{idCliente}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Realiza resgate")
    public ResponseEntity<?> RealizaResgate(@PathVariable("idVendedor") final String usernameVendedor, @PathVariable("idCliente") final String usernameCliente) {
        try {
            return new ResponseEntity<ContaCorrenteDTO>(contacorrenteService.realizaResgate(usernameCliente, usernameVendedor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{idVendedor}/full", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consulta Movimento de todos os clientes")
    public ResponseEntity<List<ContaCorrenteDTO>> ConsultaHistoricoConta(
            @PathVariable("idVendedor") final Integer idVendedor) {

        List<ContaCorrenteDTO> oiee = new ArrayList<>();
        return new ResponseEntity<List<ContaCorrenteDTO>>(oiee, HttpStatus.OK);

    }

    @GetMapping(value = "/{idVendedor}/DashCliente/{idCliente}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consulta Dashboard do cliente")
    public ResponseEntity<?> ConsultaDashBoardCliente(@PathVariable("idVendedor") String usernameVendedor, @PathVariable("idCliente") final String usernameCliente) {

        try {

            return new ResponseEntity<ClienteDashDTO>(contacorrenteService.buscaDashCliente(usernameCliente, usernameVendedor), HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping(value = "/{idVendedor}/DashTodosClientes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consulta todos os clientes do vendedor contendo informações de consumo de compras para dashboard")
    public ResponseEntity<?> ConsultaListaDashCliente(@PathVariable("idVendedor") String usernameVendedor) {

        try {
            List<ClienteDashDTO> ListaClientesRetorno =  new ArrayList<>();
            VendedorListaClientesDTO listaClienteEntity = vendedorCliente.buscaClientesDoVendedor(usernameVendedor);
            listaClienteEntity.getClientes().forEach(x -> {
                try {
                    ListaClientesRetorno.add(contacorrenteService.buscaDashCliente(x.getUsername(), usernameVendedor));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            return new ResponseEntity<List<ClienteDashDTO>>(ListaClientesRetorno, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping(value = "/{idVendedor}/BuscaUltimosResgates", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Busca todos os resgates dos clientes do vendedor informado")
    public ResponseEntity<?> ConsultaResgatesDosSeusCliente(@PathVariable("idVendedor") String usernameVendedor) {

        try {

            return new ResponseEntity<List<UltimoResgateDTO>>(contacorrenteService.buscaUltimoResgateDosClientes(usernameVendedor), HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


}
