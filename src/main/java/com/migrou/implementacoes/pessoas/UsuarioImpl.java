package com.migrou.implementacoes.pessoas;

import com.migrou.implementacoes.pessoas.bo.ClienteBO;
import com.migrou.implementacoes.pessoas.bo.VendedorBO;
import com.migrou.interfaces.cliente.ClienteInterface;
import com.migrou.interfaces.usuario.PerfilJPA;
import com.migrou.interfaces.usuario.UsuarioInterface;
import com.migrou.interfaces.usuario.UsuarioJPA;
import com.migrou.interfaces.vendedor.VendedorInterface;
import com.migrou.types.dto.PessoaDTO;
import com.migrou.types.dto.PessoaFotoDTO;
import com.migrou.types.entity.ClienteEntity;
import com.migrou.types.entity.Perfil;
import com.migrou.types.entity.Usuario;
import com.migrou.types.entity.VendedorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            try {
                usuario = usuarioJPA.save(usuario);
            }catch (Exception w) {
                w.printStackTrace();
            }
        } else {
            // se usuario já existe mas com outro perfil
            Usuario userExistente = usuarioExistente.get();
            if (userExistente.getPerfis().stream().filter(x -> x.getNome().equals(pessoaDTO.getTipoPessoa())).count() == 0L) {
                if (pessoaDTO.getTipoPessoa().equals("VENDEDOR")) {
                    criaVendedorEntity(pessoaDTO, userExistente);
                } else {
                    criaClienteEntity(pessoaDTO, userExistente);
                }
                try {
                    usuario = usuarioJPA.save(userExistente);
                } catch(Exception q){
                    q.printStackTrace();
                }
            } else {
                throw new Exception(pessoaDTO.getTipoPessoa() + " ja cadastrado");
            }
        }


        return usuario;
    }

    @Override
    @Transactional
    public Usuario salvaFoto(PessoaFotoDTO pessoaFotoDTO) throws Exception {

        if (Objects.isNull(pessoaFotoDTO.getUrlFoto()) || pessoaFotoDTO.getUrlFoto().isEmpty()) {
            throw new Exception("Informe a URL da foto");
        }

        Usuario user = usuarioJPA.findByUsername(pessoaFotoDTO.getUsername()).orElseThrow(() ->  new Exception("Usuário não encontrado"));
        if(!Objects.isNull(user.getCliente())){
            user.getCliente().setUrlFoto(pessoaFotoDTO.getUrlFoto());
        }
        if(!Objects.isNull(user.getVendedor())){
            user.getVendedor().setUrlFoto(pessoaFotoDTO.getUrlFoto());
        }

        return usuarioJPA.save(user);
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

    private void criaVendedorEntity(PessoaDTO pessoaDTO, Usuario usuario) throws Exception {

        if (Objects.isNull(pessoaDTO.getSegmentoComercial()) || pessoaDTO.getSegmentoComercial().isEmpty()){
            throw new Exception("Segmento comercial nao pode ser nulo");
        }
        if (Objects.isNull(pessoaDTO.getNomeNegocio()) || pessoaDTO.getNomeNegocio().isEmpty()){
            throw new Exception("Nome do negócio não pode ser nulo");
        }
        if (Objects.isNull(pessoaDTO.getNrCelular()) || pessoaDTO.getNrCelular().isEmpty()){
            throw new Exception("Numero do celular não pode ser nulo");
        }

        VendedorEntity vendedorEntity = new VendedorEntity();
        Set<Perfil> perfils = usuario.getPerfis();


        vendedorEntity.setUsername(pessoaDTO.getEmail());
        vendedorEntity.setNome(pessoaDTO.getNome());
        vendedorEntity.setCpfCnpj(pessoaDTO.getCpfCnpj());
        vendedorEntity.setDtCadastro(new Date());
        vendedorEntity.setDtNascimento(pessoaDTO.getDataNascimento());
        vendedorEntity.setNrCelular(pessoaDTO.getNrCelular());
        vendedorEntity.setNomeSegmento(pessoaDTO.getSegmentoComercial());
        vendedorEntity.setNomeNegocio(pessoaDTO.getNomeNegocio());
        usuario.setVendedor(vendedorEntity);
        perfils.add(new Perfil(2l, "VENDEDOR"));
        usuario.setPerfis(perfils);
    }

}