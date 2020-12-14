package ru.innopolis.stc.springbootexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ru.innopolis.stc.springbootexample.services.UserService;

@SpringBootApplication
//@Import({"ru.innopolis.stc.springbootexample.config.MyConfiguration"})
public class SpringBootExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootExampleApplication.class, args);
  }

}
