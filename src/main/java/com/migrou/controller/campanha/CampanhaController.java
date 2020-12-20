package com.migrou.controller.campanha;

import com.migrou.implementacoes.campanha.CampanhaBO;
import com.migrou.implementacoes.campanha.CampanhaImpl;
import com.migrou.implementacoes.pessoas.bo.ClienteBO;
import com.migrou.interfaces.cliente.ClienteInterface;
import com.migrou.types.dto.CampanhaDTO;
import com.migrou.types.dto.ClienteDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ClienteDTO> AtribuiCampanhaAoCliente(@PathVariable("IdCampanha") Integer  idCampanha, @PathVariable("email") String usernameCliente)  {
        try{
        	ClienteDTO clienteDTO = new ClienteDTO();
        	clienteDTO.setIdCampanha(idCampanha);
        	clienteDTO.setUsername(usernameCliente);
            return new ResponseEntity<ClienteDTO>(clienteBO.parsePojoToDTO(clienteInterface.atribuirCampanha(clienteDTO)), HttpStatus.OK );
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
