package ru.innopolis.stc.springbootexample.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import ru.innopolis.stc.springbootexample.SpringBootExampleApplication;
import ru.innopolis.stc.springbootexample.repositories.entities.User;

@SpringBootTest
@ContextConfiguration(classes = {SpringBootExampleApplication.class})
@ActiveProfiles({"local", "in-memory"})
public class UserServiceTest {

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
