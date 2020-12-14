package ru.innopolis.stc.springbootexample.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.innopolis.stc.springbootexample.repositories.InMemoryUserDao;
import ru.innopolis.stc.springbootexample.repositories.UserDao;
import ru.innopolis.stc.springbootexample.security.AuthSuccessHandler;
import ru.innopolis.stc.springbootexample.security.Base64PassEncoder;
import ru.innopolis.stc.springbootexample.security.MD5PassEncoder;
import ru.innopolis.stc.springbootexample.services.UserService;

@Configuration
public class MyConfiguration {

  @Bean("passEncoder")
  @Profile("base64")
  public PasswordEncoder base64PassEncoder() {
    return new Base64PassEncoder();
  }

  @Bean("passEncoder")
  @Profile("md5")
  public PasswordEncoder md5PassEncoder() {
    return new MD5PassEncoder();
  }

  @Bean("passEncoder")
  @Profile("bcrypt")
  public PasswordEncoder bcryptPassEncoder() {
    return new BCryptPasswordEncoder(42);
  }

  @Bean
  public UserDao userRepository(PasswordEncoder encoder) {
    return new InMemoryUserDao(encoder);
  }

  @Bean
  @Primary
  public UserService userService(UserDao repository) {
    return new UserService(repository);
  }

  @Bean
  public AuthenticationSuccessHandler authSuccessHandler(UserDao dao) {
    return new AuthSuccessHandler(dao);
  }

  @Bean
  public HealthIndicator some() {
    return new HealthIndicator() {
      @Override
      public Health health() {
        String[] errors = getErrors();
        if (errors != null && errors.length>0) {
          return Health.down().withDetail("Errors detected",
              String.join("\n", errors)).build();
        }
        return Health.up().build();
      }

      public String[] getErrors() {
        return null;
      }
    };
  }
}
