package com.migrou.implementacoes.auth;

import com.migrou.implementacoes.pessoas.PessoaImpl;
import com.migrou.types.dto.PessoaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    PessoaImpl pessoaService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<GrantedAuthority> authority = null;
        System.out.println("PASSO loadUserByUsername");
        BCryptPasswordEncoder encoder = passwordEncoder();
        PessoaDTO pessoaDTO = Optional.ofNullable(pessoaService.getPessoaByEmail(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (pessoaDTO.getTipoPessoa().compareTo("AMBAS")==0) {
            authority = AuthorityUtils.createAuthorityList("ROLE_VENDEDOR", "ROLE_CLIENTE");
        } else if (pessoaDTO.getTipoPessoa().compareTo("CLIENTE")==0) {
            authority = AuthorityUtils.createAuthorityList("ROLE_CLIENTE");
        } else if (pessoaDTO.getTipoPessoa().compareTo("VENDEDOR")==0){
            authority = AuthorityUtils.createAuthorityList("ROLE_VENDEDOR");
        }

        return new User(pessoaDTO.getEmail(),encoder.encode(pessoaDTO.getSenha()), authority);

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
