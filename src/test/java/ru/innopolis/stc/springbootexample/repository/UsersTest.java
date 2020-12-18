package ru.innopolis.stc.springbootexample.repository;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.innopolis.stc.springbootexample.SpringBootExampleApplication;
import ru.innopolis.stc.springbootexample.repositories.entities.User;
import ru.innopolis.stc.springbootexample.services.UserService;

@DataJpaTest
@ContextConfiguration(
    initializers = ContextInitializer.class,
    classes = {SpringBootExampleApplication.class})
@ActiveProfiles({"local", "db"})
@PropertySource(name = "spring.datasource.url", value = "jdbc:h2:mem:test")
@PropertySource(name = "spring.jpa.properties.hibernate.dialect", value = "org.hibernate.dialect.H2Dialect")
@TestPropertySource(locations = "classpath:application-integration.yml")
@DirtiesContext
class UsersTest {

  @Autowired
  private UserService userService;


  @Test
  public void testUsersGetAll() throws Exception {
    final Collection<User> allUsers = userService.getAllUsers();
    System.out.println(allUsers);
  }

}
