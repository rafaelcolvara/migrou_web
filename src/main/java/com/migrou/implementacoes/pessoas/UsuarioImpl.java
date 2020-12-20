package com.migrou.implementacoes.pessoas;

import com.migrou.implementacoes.pessoas.bo.ClienteBO;
import com.migrou.implementacoes.pessoas.bo.VendedorBO;
import com.migrou.interfaces.cliente.ClienteInterface;
import com.migrou.interfaces.usuario.PerfilJPA;
import com.migrou.interfaces.usuario.UsuarioInterface;
import com.migrou.interfaces.usuario.UsuarioJPA;
import com.migrou.interfaces.vendedor.VendedorInterface;
import com.migrou.types.dto.ClienteDTO;
import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.dto.VendedorDTO;
import com.migrou.types.entity.ClienteEntity;
import com.migrou.types.entity.Perfil;
import com.migrou.types.entity.Usuario;
import com.migrou.types.entity.VendedorEntity;
import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.Perf;

import java.util.*;
import java.util.stream.Collectors;

@Service("usuarioService")
public class UsuarioImpl implements UsuarioInterface {

    @Autowired
    UsuarioJPA usuarioJPA;

    @Autowired
    PerfilJPA perfilJPA;

    @Autowired
    ClienteBO clienteBO;

    @Autowired
    VendedorBO vendedorBO;

    @Autowired
    VendedorInterface vendedorInterface;

    @Autowired
    ClienteInterface clienteInterface;

    @Override
    @Transactional
    public Usuario salva(PessoaDTO pessoaDTO) throws Exception {

        List<Perfil> perfils = new ArrayList<>();
        perfils = perfilJPA.findAll();

        Usuario usuario = new Usuario();
        Optional<Usuario> usuarioExistente = usuarioJPA.findByUsername(pessoaDTO.getEmail());
        if (!usuarioExistente.isPresent()) {
            usuario.setUsername(pessoaDTO.getEmail());
            usuario.setPassword(new BCryptPasswordEncoder().encode(pessoaDTO.getSenha()));
            usuario.setPerfis(perfils.stream().filter(x -> x.getAuthority().equals(pessoaDTO.getTipoPessoa())).collect(Collectors.toSet()));
            if (usuario.getPerfis().contains(new Perfil(1L, "CLIENTE"))) {

                criaClienteEntity(pessoaDTO, usuario);
            }
            if (usuario.getPerfis().contains(new Perfil(2L, "VENDEDOR"))) {

                criaVendedorEntity(pessoaDTO, usuario);
            }
            usuario = usuarioJPA.save(usuario);
        } else {
            // se usuario já existe mas com outro perfil
            Usuario userExistente = usuarioExistente.get();
            if (userExistente.getPerfis().stream().filter(x -> x.getNome().equals(pessoaDTO.getTipoPessoa())).count() == 0L) {
                if (pessoaDTO.getTipoPessoa().equals("VENDEDOR")) {
                    criaVendedorEntity(pessoaDTO, userExistente);
                } else {
                    criaClienteEntity(pessoaDTO, userExistente);
                }
                usuario = usuarioJPA.save(userExistente);
            } else {
                throw new Exception(pessoaDTO.getTipoPessoa() + " ja cadastrado");
            }
        }
        return usuario;
    }

    private void criaClienteEntity(PessoaDTO pessoaDTO, Usuario usuario) {
        ClienteEntity cliente = new ClienteEntity();
        Set<Perfil> perfils = usuario.getPerfis();
        cliente.setUsername(pessoaDTO.getEmail());
        cliente.setCpfCnpj(pessoaDTO.getCpfCnpj());
        cliente.setNome(pessoaDTO.getNome());
        cliente.setDtCadastro(new Date());
        perfils.add(new Perfil(1l, "CLIENTE"));
        usuario.setPerfis(perfils);
        usuario.setCliente(cliente);
    }

    private void criaVendedorEntity(PessoaDTO pessoaDTO, Usuario usuario) {
        VendedorEntity vendedorEntity = new VendedorEntity();
        Set<Perfil> perfils = usuario.getPerfis();
        vendedorEntity.setUsername(pessoaDTO.getEmail());
        vendedorEntity.setNome(pessoaDTO.getNome());
        vendedorEntity.setCpfCnpj(pessoaDTO.getCpfCnpj());
        vendedorEntity.setDtCadastro(pessoaDTO.getDataCadastro());
        vendedorEntity.setDtNascimento(pessoaDTO.getDataNascimento());
        vendedorEntity.setNomeSegmento(pessoaDTO.getSegmentoComercial());
        vendedorEntity.setNomeNegocio(pessoaDTO.getNomeNegocio());
        usuario.setVendedor(vendedorEntity);
        perfils.add(new Perfil(2l, "VENDEDOR"));
        usuario.setPerfis(perfils);
    }

}