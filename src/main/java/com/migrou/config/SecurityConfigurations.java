package com.migrou.config;

import com.migrou.implementacoes.auth.TokenService;
import com.migrou.interfaces.pessoas.PessoaInterface;
import com.migrou.interfaces.usuario.UsuarioJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioJPA usuarioJPA;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    // Configurações de autenticação (login)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configurações de autorização por URL
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().authorizeRequests()
        .antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
        .antMatchers(HttpMethod.POST, "/usuario/login").permitAll()
        .antMatchers("/webjars/**").permitAll()
        .antMatchers("/swagger-resources/**").permitAll()
        .antMatchers("/v2/api-docs").permitAll()
        .anyRequest()
        .authenticated()
        .and().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioJPA), UsernamePasswordAuthenticationFilter.class);
    }

    //Configurações de recursos estáticos( requisições para JS, Imagens)
    @Override
    public void configure(WebSecurity web) throws Exception {

    }

}
