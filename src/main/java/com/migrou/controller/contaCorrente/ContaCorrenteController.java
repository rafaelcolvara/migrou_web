package com.migrou.controller.contaCorrente;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.migrou.types.dto.ClienteDashDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.migrou.implementacoes.contaCorrente.ContaCorrenteImpl;
import com.migrou.types.dto.ContaCorrenteDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/contaCorrente")
@Api(value = "Api de administração de lançamentos em conta corrente")
@CrossOrigin(origins = "*")
public class ContaCorrenteController {

    @Autowired
    ContaCorrenteImpl contacorrenteService;

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
    public ResponseEntity<?> RealizaResgate(@PathVariable("idVendedor") final UUID idVendedor, @PathVariable("idCliente") final UUID idCliente) {
        try {
            return new ResponseEntity<ContaCorrenteDTO>(contacorrenteService.realizaResgate(idCliente, idVendedor), HttpStatus.OK);
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
    public ResponseEntity<?> ConsultaDashBoardCliente(@PathVariable("idVendedor") UUID idVendedor, @PathVariable("idCliente") final UUID idCliente) {

        try {

            return new ResponseEntity<ClienteDashDTO>(contacorrenteService.buscaDashCliente(idCliente, idVendedor), HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
