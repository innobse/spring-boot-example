package ru.innopolis.stc.springbootexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.innopolis.stc.springbootexample.security.Base64PassEncoder;
import ru.innopolis.stc.springbootexample.security.MD5PassEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
//        .antMatchers("/**").permitAll();
        .antMatchers("/error").permitAll()
        .antMatchers("/actuator/**").permitAll()
        .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
        .antMatchers("/user/**").access("hasRole('ROLE_USER')")
        .antMatchers("/users").access("hasRole('ROLE_USER')")
        .and().formLogin().defaultSuccessUrl("/", false)
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login")
        .permitAll();

  }
}
