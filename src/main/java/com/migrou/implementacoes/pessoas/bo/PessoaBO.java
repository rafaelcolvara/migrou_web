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
        pessoaDto.setId(pessoaEntity.getIdPessoa().toString());
        pessoaDto.setSenha(pessoaEntity.getSenha());
        pessoaDto.setDataNascimento(pessoaEntity.getDtNascimento());
        pessoaDto.setBase64Foto(pessoaEntity.getFoto()!=null?Base64.getEncoder().encodeToString(pessoaEntity.getFoto()):null);
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
        pessoaDto.setId(pessoaEntity.getIdPessoa().toString());
        pessoaDto.setSenha(pessoaEntity.getSenha());
        pessoaDto.setNomeNegocio(pessoaEntity.getNomeNegocio());
        pessoaDto.setSegmentoComercial(pessoaEntity.getNomeSegmento());
        pessoaDto.setDataNascimento(pessoaEntity.getDtNascimento());
        pessoaDto.setBase64Foto(pessoaEntity.getFoto()!=null?Base64.getEncoder().encodeToString(pessoaEntity.getFoto()):null);
        pessoaDto.setFlgEmailValido(pessoaEntity.getFlgEmailValido());
        pessoaDto.setNrCelular(pessoaEntity.getNrCelular());
        return pessoaDto;
    }

    public ClienteEntity parseDtoToPojo(PessoaDTO pessoaDTO) {

        ClienteEntity pessoaEntity = new ClienteEntity();

        pessoaEntity.setIdPessoa(pessoaDTO.getId()!=null?UUID.fromString(pessoaDTO.getId()):null);
        pessoaEntity.setCpfCnpj(pessoaDTO.getCpfCnpj());
        pessoaEntity.setDtCadastro(pessoaDTO.getDataCadastro());
        pessoaEntity.setNome(pessoaDTO.getNome());
        pessoaEntity.setEmail(pessoaDTO.getEmail());
        pessoaEntity.setDtNascimento(pessoaDTO.getDataNascimento());
        pessoaEntity.setSenha(pessoaDTO.getSenha());
        pessoaEntity.setFoto(pessoaDTO.getBase64Foto()!=null?pessoaDTO.getBase64Foto().getBytes():null);
        pessoaEntity.setFlgEmailValido(pessoaDTO.isFlgEmailValido());
        pessoaEntity.setNrCelular(pessoaDTO.getNrCelular());
        return pessoaEntity;
    }


    public PessoaFotoDTO parsePojoToDtoFoto(PessoaEntity pessoaEntity) {
        PessoaFotoDTO pessoaDto = new PessoaFotoDTO();
        pessoaDto.setIdPessoa(pessoaEntity.getIdPessoa());
        pessoaDto.setByteArrayFoto(Base64.getEncoder().encodeToString(pessoaEntity.getFoto()));
        return pessoaDto;
    }

    public PessoaEntity parseDtoToPojoFoto(PessoaFotoDTO pessoaDTO) {

        PessoaEntity pessoaEntity = new PessoaEntity();
        pessoaEntity.setIdPessoa(pessoaDTO.getIdPessoa());
        pessoaEntity.setFoto(pessoaDTO.getByteArrayFoto().getBytes());
        return pessoaEntity;
    }





}
