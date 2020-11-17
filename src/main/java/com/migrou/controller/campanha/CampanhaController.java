package com.migrou.controller.campanha;

import java.util.List;
import java.util.UUID;

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

import com.migrou.implementacoes.campanha.CampanhaBO;
import com.migrou.implementacoes.campanha.CampanhaImpl;
import com.migrou.implementacoes.pessoas.bo.ClienteBO;
import com.migrou.interfaces.pessoas.ClienteInterface;
import com.migrou.types.dto.CampanhaDTO;
import com.migrou.types.dto.ClienteDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/campanha")
@Api(value = "Api de administração de campanhas")
@CrossOrigin(origins = "*")
public class CampanhaController {

    @Autowired
    CampanhaImpl campanhaService;

    @Autowired
    ClienteInterface clienteInterface;
    
    @Autowired
    ClienteBO clienteBO;

    
    @Autowired
    CampanhaBO campanhaBO;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Cadastra campanha de vinculação do cliente")
    public ResponseEntity<CampanhaDTO> CadastraCampanha(@RequestBody CampanhaDTO  campanhaDTO) {
        try{
            return new ResponseEntity<CampanhaDTO>(campanhaBO.parsePojoToDTO(campanhaService.cadastraCampanha(campanhaBO.parseDTOtoPojo(campanhaDTO))), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PatchMapping(value = "/{IdCampanha}/cliente/{IdPessoa}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Vincula campanha ao cliente")
    public ResponseEntity<ClienteDTO> AtribuiCampanhaAoCliente(@PathVariable("IdCampanha") Integer  idCampanha, @PathVariable("IdPessoa") UUID idPessoa)  {
        try{
        	return new ResponseEntity<ClienteDTO>(clienteBO.parsePojoToDTO(clienteInterface.atribuirCampanha(idPessoa, idCampanha)), HttpStatus.OK );
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),  HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/buscaTodos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<CampanhaDTO>> ConsultaTodasCampanhas() {
        try{
        	return new ResponseEntity<List<CampanhaDTO>>(campanhaService.buscaTodos(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
