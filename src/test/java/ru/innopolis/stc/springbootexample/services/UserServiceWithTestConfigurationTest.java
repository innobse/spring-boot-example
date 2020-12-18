package ru.innopolis.stc.springbootexample.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.innopolis.stc.springbootexample.SpringBootExampleApplication;
import ru.innopolis.stc.springbootexample.repositories.UserDao;
import ru.innopolis.stc.springbootexample.repositories.entities.User;
import ru.innopolis.stc.springbootexample.services.UserServiceWithTestConfigurationTest.UserTestConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {SpringBootExampleApplication.class, UserTestConfiguration.class})
@ActiveProfiles({"local"})
public class UserServiceWithTestConfigurationTest {

  @TestConfiguration
  static class UserTestConfiguration {

    @Bean
    public UserDao userDao() {
      return new UserDao() {

        private Map<String, User> keeper = new HashMap<>();

        @Override
        public User addUser(User user) {
          keeper.put(user.getLogin(), user);
          return user;
        }

        @Override
        public User getUserByLogin(String userLogin) {
          return keeper.get(userLogin);
        }

        @Override
        public Collection<User> getAll() {
          return keeper.values();
        }
      };
    }
  }

  @Autowired
  private UserService userService;

  @Test
  public void testAddNewUser() {

    final String targetUsername = "newUser";
    final User newUser = userService.createNewUser(
        new User(targetUsername, "password", "ROLE_USER")
    );

    final User userByUsername = userService.getUserByUsername(targetUsername);

    assertNotNull(userByUsername);
    assertEquals(newUser, userByUsername);
    assertEquals(targetUsername, userByUsername.getLogin());
    assertEquals("password", userByUsername.getPassword());
    assertEquals(1, userByUsername.getRoles().length);
    assertEquals("ROLE_USER", userByUsername.getRoles()[0]);
  }

  @Test
  public void testGetAllUsers() {

    final Collection<User> allUsers = userService.getAllUsers();

    assertNotNull(allUsers);
    assertNotEquals(0, allUsers.size());
  }

}
