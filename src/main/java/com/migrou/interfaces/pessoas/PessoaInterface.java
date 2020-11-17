package com.migrou.interfaces.pessoas;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.primitives.Bytes;
import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.dto.PessoaFotoDTO;
import com.migrou.types.entity.PessoaEntity;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.UUID;

public interface PessoaInterface {
    PessoaDTO cadastraPessoa(PessoaDTO pessoaDTO) throws Exception;
    void apagaPessoa(UUID idPessoa) throws Exception;
    PessoaEntity consutaClientePorEmail(String email);
    PessoaDTO consultaPorEmaileSenha(String email, String Senha, String tipoPessoa)  throws Exception;
    void AtualizaFoto(PessoaFotoDTO pessoaFoto)  throws Exception;
    PessoaFotoDTO consultaFotoPefilPorID(UUID idPessoa) throws Exception;
    void EnviaEmail(PessoaDTO pessoaDTO) throws AddressException, MessagingException, IOException;
	void AtivaViaEmail(PessoaDTO pessoaDTO) throws Exception;
}
