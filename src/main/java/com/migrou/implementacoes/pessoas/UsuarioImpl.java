package com.migrou.implementacoes.usuario;

import com.migrou.interfaces.usuario.PerfilJPA;
import com.migrou.interfaces.usuario.UsuarioInterface;
import com.migrou.interfaces.usuario.UsuarioJPA;
import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.entity.Perfil;
import com.migrou.types.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("usuarioService")
public class UsuarioImpl implements UsuarioInterface {

    @Autowired
    UsuarioJPA usuarioJPA;

    @Autowired
    PerfilJPA perfilJPA;

    @Override
    public Usuario salva(PessoaDTO pessoaDTO) {

        List<Perfil> perfils = new ArrayList<>();
        perfils = perfilJPA.findAll();

        Usuario usuario = new Usuario();
        usuario.setUsername(pessoaDTO.getEmail());
        usuario.setPassword(new BCryptPasswordEncoder().encode(pessoaDTO.getSenha()));

        usuario.setPerfis(perfils.stream().filter(x -> x.getAuthority().equals(pessoaDTO.getTipoPessoa())).collect(Collectors.toList()));

        return usuarioJPA.save(usuario);

    }

}