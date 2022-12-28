package com.proyectoagendador.multicom.config;

import com.proyectoagendador.multicom.security.TokenEntryPointSecurity;
import lombok.RequiredArgsConstructor;

import com.proyectoagendador.multicom.security.TokenFilterSecurity;
import com.proyectoagendador.multicom.security.CustomAccessDeniedHandler;
import com.proyectoagendador.multicom.service.maintenences.user.UserDetailPrincipalService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailPrincipalService service;
    private final TokenEntryPointSecurity security;

    @Bean
    public
    AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public
    TokenFilterSecurity jwtTokenFilter(){
        return new TokenFilterSecurity();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/authentication/**")
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/category/findBy/**", "/category/findAll", "/product/findById", "/product/findAll")
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/**/register", "/**/update")
                .hasAnyRole("ADMIN");

        http.authorizeRequests()
                .antMatchers("/customer/**")
                .hasAnyRole("ADMIN", "MOD");

        http.cors().and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(this.accessDeniedHandler())
                .authenticationEntryPoint(this.security)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
