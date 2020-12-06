package com.migrou.implementacoes.auth;

import com.migrou.interfaces.pessoas.PessoaJPARpository;
import com.migrou.types.entity.PessoaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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
    PessoaJPARpository pessoaJPARpository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = passwordEncoder();
        PessoaEntity pessoaEntity = Optional.ofNullable(pessoaJPARpository.findbyEmailIgnoreCase(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorityAdminList = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> authorityUserList = AuthorityUtils.createAuthorityList("ROLE_USER");

        return new User(pessoaEntity.getEmail(),encoder.encode(pessoaEntity.getSenha()), authorityAdminList );

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
