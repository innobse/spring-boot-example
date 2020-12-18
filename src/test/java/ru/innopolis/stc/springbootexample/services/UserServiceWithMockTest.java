package ru.innopolis.stc.springbootexample.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.innopolis.stc.springbootexample.SpringBootExampleApplication;
import ru.innopolis.stc.springbootexample.repositories.UserDao;
import ru.innopolis.stc.springbootexample.repositories.entities.User;

@SpringBootTest
@ContextConfiguration(classes = {SpringBootExampleApplication.class})
@ActiveProfiles({"local"})
public class UserServiceWithMockTest {

  @MockBean
  private UserDao dao;

  @Autowired
  private UserService userService;

  @Test
  public void testAddNewUser() {

    final String targetUsername = "newUser";
    final User user = new User(targetUsername, "password", "ROLE_USER");

    when(dao.addUser(any())).thenReturn(user);
    when(dao.getUserByLogin(targetUsername)).thenReturn(user);

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
  public void testGetUser() {

    final String targetUsername = "newUser";
    final User user = new User(targetUsername, "password", "ROLE_USER");

    when(dao.getUserByLogin(targetUsername)).thenReturn(user);

    final User userByUsername = userService.getUserByUsername(targetUsername);

    assertNotNull(userByUsername);
    assertEquals(user, userByUsername);
    assertEquals(targetUsername, userByUsername.getLogin());
    assertEquals("password", userByUsername.getPassword());
    assertEquals(1, userByUsername.getRoles().length);
    assertEquals("ROLE_USER", userByUsername.getRoles()[0]);
  }

}
