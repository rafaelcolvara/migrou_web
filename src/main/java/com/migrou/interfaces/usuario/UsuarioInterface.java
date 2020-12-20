package com.migrou.interfaces.usuario;

import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.entity.Usuario;

public interface UsuarioInterface {

    Usuario salva(PessoaDTO pessoaDTO) throws Exception;
}
