package com.migrou.controller.pessoa;

import com.migrou.implementacoes.pessoas.PessoaImpl;
import com.migrou.implementacoes.pessoas.bo.PessoaBO;
import com.migrou.interfaces.pessoas.PessoaJPARpository;
import com.migrou.types.dto.LoginDTO;
import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.dto.PessoaFotoDTO;
import com.migrou.types.entity.PessoaEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@RestController
@RequestMapping(value = "/pessoas")
@Api(value = "Api de consulta a pessoas do sistema")
@CrossOrigin(origins = "*")
public class PessoasController {

    @Autowired
    PessoaJPARpository pessoaJPARepository;

    @Autowired
    PessoaImpl pessoaService;

    @Autowired
    PessoaBO pessoaBO;

    @GetMapping(value = "/nome/{nome}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retorna uma lista de usuarios informando uma parte do nome")
    public ResponseEntity<List<PessoaDTO>> ConsultaNomeUsuario(@PathVariable String nome) {

        List<PessoaEntity> pessoasEntities = pessoaJPARepository.findbyLikeNomeIgnoreCase(nome);

        List<PessoaDTO> pessoaDto = new ArrayList<>();
        pessoasEntities.stream().forEach(u -> {
            pessoaDto.add(pessoaBO.parsePojoToDto(u));
        });
        return new ResponseEntity<List<PessoaDTO>>(pessoaDto, HttpStatus.OK);
    }

    @GetMapping(value = "/id/{idPessoa}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retorna uma lista de usuarios informando uma parte do nome")
    public ResponseEntity<PessoaDTO> ConsultaporId(@PathVariable("idPessoa") UUID idPessoa) {

        PessoaEntity pessoa = pessoaJPARepository.findById(idPessoa).get();

        PessoaDTO pessoaDto = pessoaBO.parsePojoToDto(pessoa);

        return new ResponseEntity<PessoaDTO>(pessoaDto, HttpStatus.OK);

    }

    @PostMapping(value = "/inclui" , consumes =  MediaType.APPLICATION_JSON_VALUE, produces =  MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Cadastra um pessoa no sistema")
    public ResponseEntity<PessoaDTO> CadastraPessoa(@RequestBody PessoaDTO pessoaDTO)  {
        PessoaDTO pessoaDTOreturn = new PessoaDTO();
        try {
            pessoaDTOreturn = pessoaService.cadastraPessoa(pessoaDTO);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(pessoaDTOreturn);

    }
    
    @GetMapping(value = "/{email}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consulta Pessoa por email")
    public ResponseEntity<PessoaDTO> ConsultaCliente(@PathVariable("email") final String email) {
    	
    	PessoaDTO pessoaDTO = new PessoaDTO();
		try {
            pessoaDTO = pessoaBO.parsePojoToDto(pessoaService.consutaClientePorEmail(email));
			
		}catch (Exception e)
		{
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<PessoaDTO>(pessoaDTO, HttpStatus.OK);
	}


    @PatchMapping(value = "/foto" )
    public ResponseEntity<String> AtualizaFoto(@RequestBody PessoaFotoDTO pessoaDTO){
    	
    	try {
			pessoaService.AtualizaFoto(pessoaDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new ResponseEntity<String>(HttpStatus.OK);
    	
    }

    @GetMapping(value = "/foto/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consulta foto do perfil da pessoa")
    public ResponseEntity<PessoaFotoDTO> ConsultaFotoPerfil(@PathVariable("id") final UUID idPessoa) {

        PessoaFotoDTO pessoaFtDto = new PessoaFotoDTO();
        try {
            pessoaFtDto = pessoaService.consultaFotoPefilPorID(idPessoa);

        }catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<PessoaFotoDTO>(pessoaFtDto, HttpStatus.OK);
    }

    @GetMapping(value = "/email/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Envia email")
    public ResponseEntity<String> EnviaEmail(@PathVariable("id") final UUID idPessoa) {


        try {
            PessoaEntity pessoaEntity = pessoaJPARepository.findById(idPessoa).get();
            pessoaService.EnviaEmail(pessoaBO.parsePojoToDto(pessoaEntity));

        }catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("nada", HttpStatus.OK);
    }

    @GetMapping(value = "/ativacao/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Envia email")
    public ResponseEntity<String> AtivacaoViaEmail(@PathVariable("id") final UUID idPessoa) {


        try {
            PessoaEntity pessoaEntity = pessoaJPARepository.findById(idPessoa).get();
            pessoaService.EnviaEmail(pessoaBO.parsePojoToDto(pessoaEntity));

        }catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<String>("nada", HttpStatus.OK);
    }



}
