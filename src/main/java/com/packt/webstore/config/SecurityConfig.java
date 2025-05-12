package com.packt.webstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    public void configurerGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication().withUser("john").password("john").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("admin123").roles("USER","ADMIN");
    }
    @Override
protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.formLogin()
        .loginPage("/login")
        .usernameParameter("userId")
        .passwordParameter("password")
        .defaultSuccessUrl("/market/products/add")
        .failureUrl("/login?error");

    httpSecurity.logout().logoutSuccessUrl("/login?logout");
    httpSecurity.exceptionHandling().accessDeniedPage("/login?accessDenied");

    httpSecurity.authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/**/add").access("hasRole('ADMIN')")
        .antMatchers("/**/market/**").access("hasRole('USER')");

    httpSecurity.csrf().disable();  // Solo para pruebas, en producción se debe habilitar

    // Agregar configuración de sesión
    httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

    }
}
