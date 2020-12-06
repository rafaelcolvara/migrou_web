package com.migrou.implementacoes.pessoas.bo;

import com.migrou.types.dto.PessoaFotoDTO;
import com.migrou.types.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.migrou.interfaces.vendedor.VendedorJPARepository;
import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.entity.PessoaEntity;
import com.migrou.types.entity.VendedorEntity;

import java.util.Base64;
import java.util.UUID;

@Component
public class PessoaBO<T extends PessoaEntity> {

    @Autowired
    VendedorJPARepository vendedorJPARepository;

    public PessoaDTO parsePojoToDto(T pessoaEntity) {
        PessoaDTO pessoaDto = new PessoaDTO();
        pessoaDto.setCpfCnpj(pessoaEntity.getCpfCnpj());
        pessoaDto.setDataCadastro(pessoaEntity.getDtCadastro());
        pessoaDto.setEmail(pessoaEntity.getEmail());
        pessoaDto.setNome(pessoaEntity.getNome());
        pessoaDto.setId(UUID.fromString(pessoaEntity.getIdPessoa().toString()));
        pessoaDto.setSenha(pessoaEntity.getSenha());
        pessoaDto.setDataNascimento(pessoaEntity.getDtNascimento());
        pessoaDto.setUrlFoto(pessoaEntity.getUrlFoto());
        pessoaDto.setFlgEmailValido(pessoaEntity.getFlgEmailValido());
        pessoaDto.setNrCelular(pessoaEntity.getNrCelular());
        return pessoaDto;
    }
    public PessoaDTO parsePojoToDto(VendedorEntity pessoaEntity) {
        PessoaDTO pessoaDto = new PessoaDTO();
        pessoaDto.setCpfCnpj(pessoaEntity.getCpfCnpj());
        pessoaDto.setDataCadastro(pessoaEntity.getDtCadastro());
        pessoaDto.setEmail(pessoaEntity.getEmail());
        pessoaDto.setNome(pessoaEntity.getNome());
        pessoaDto.setId(UUID.fromString(pessoaEntity.getIdPessoa().toString()));
        pessoaDto.setSenha(pessoaEntity.getSenha());
        pessoaDto.setNomeNegocio(pessoaEntity.getNomeNegocio());
        pessoaDto.setSegmentoComercial(pessoaEntity.getNomeSegmento());
        pessoaDto.setDataNascimento(pessoaEntity.getDtNascimento());
        pessoaDto.setUrlFoto(pessoaEntity.getUrlFoto());
        pessoaDto.setFlgEmailValido(pessoaEntity.getFlgEmailValido());
        pessoaDto.setNrCelular(pessoaEntity.getNrCelular());
        return pessoaDto;
    }

    public ClienteEntity parseDtoToPojo(PessoaDTO pessoaDTO) {

        ClienteEntity pessoaEntity = new ClienteEntity();

        pessoaEntity.setIdPessoa(pessoaDTO.getId()!=null?pessoaDTO.getId():null);
        pessoaEntity.setCpfCnpj(pessoaDTO.getCpfCnpj());
        pessoaEntity.setDtCadastro(pessoaDTO.getDataCadastro());
        pessoaEntity.setNome(pessoaDTO.getNome());
        pessoaEntity.setEmail(pessoaDTO.getEmail());
        pessoaEntity.setDtNascimento(pessoaDTO.getDataNascimento());
        pessoaEntity.setSenha(pessoaDTO.getSenha());
        pessoaEntity.setUrlFoto(pessoaDTO.getUrlFoto());
        pessoaEntity.setFlgEmailValido(pessoaDTO.isFlgEmailValido());
        pessoaEntity.setNrCelular(pessoaDTO.getNrCelular());
        return pessoaEntity;
    }


    public PessoaFotoDTO parsePojoToDtoFoto(PessoaEntity pessoaEntity) {
        PessoaFotoDTO pessoaDto = new PessoaFotoDTO();
        pessoaDto.setIdPessoa(pessoaEntity.getIdPessoa());
        pessoaDto.setUrlFoto(pessoaEntity.getUrlFoto());
        return pessoaDto;
    }

    public PessoaEntity parseDtoToPojoFoto(PessoaFotoDTO pessoaDTO) {

        PessoaEntity pessoaEntity = new PessoaEntity();
        pessoaEntity.setIdPessoa(pessoaDTO.getIdPessoa());
        pessoaEntity.setUrlFoto(pessoaDTO.getUrlFoto());
        return pessoaEntity;
    }





}
