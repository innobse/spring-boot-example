package ru.innopolis.stc.springbootexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import ru.innopolis.stc.springbootexample.services.UserService;

@SpringBootApplication
public class SpringBootExampleApplication {

  public static void main(String[] args) {
    final SpringApplication springApplication = new SpringApplication(SpringBootExampleApplication.class);
    springApplication.setBanner((environment, sourceClass, out) -> out.println(SLANT_LOGO));
    springApplication.run(args);
  }

  private static final String SLANT_LOGO =
        "   _____  ______   ______          _____   ___\n"
      + "  / ___/ /_  __/  / ____/         |__  /  <  /\n"
      + "  \\__ \\   / /    / /      ______   /_ <   / / \n"
      + " ___/ /  / /    / /___   /_____/ ___/ /  / /  \n"
      + "/____/  /_/     \\____/          /____/  /_/   ";

}
