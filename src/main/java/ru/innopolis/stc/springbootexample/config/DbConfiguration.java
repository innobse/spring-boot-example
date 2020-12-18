package ru.innopolis.stc.springbootexample.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.innopolis.stc.springbootexample.repositories.SpringDataUserDao;
import ru.innopolis.stc.springbootexample.repositories.UserDao;
import ru.innopolis.stc.springbootexample.repositories.UserRepository;

@Configuration
@Profile("db")
@EntityScan("ru.innopolis.stc.springbootexample.repositories.entities")
@Order(1000)
public class DbConfiguration extends WebSecurityConfigurerAdapter {

  @Bean
  public UserDao userDao(UserRepository repository, PasswordEncoder encoder) {
    return new SpringDataUserDao(repository, encoder);
  }
}
