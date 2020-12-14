package ru.innopolis.stc.springbootexample.repositories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.innopolis.stc.springbootexample.repositories.entities.User;

public class InMemoryUserDao implements UserDao {

  private static final Logger LOGGER = Logger.getLogger("InMemoryUserDao");

  private final Map<String, User> database;

  public InMemoryUserDao(PasswordEncoder encoder) {
    database = new HashMap<>();
    database.put("admin", new User("admin", encoder.encode("admin"), "ROLE_ADMIN", "ROLE_USER"));
    database.put("user", new User("user", encoder.encode("user"), "ROLE_USER"));
    database.put("guest", new User("guest", encoder.encode("guest"), "ROLE_USER"));
  }

  @Override
  public User addUser(User user) {
    database.put(user.getLogin(), user);
    return database.get(user.getLogin());
  }

  @Override
  public User getUserByLogin(String userLogin) {
    return database.get(userLogin);
  }

  @Override
  public Collection<User> getAll() {
    return database.values();
  }

  @PostConstruct
  public void init() {
    LOGGER.info(database.toString());
    //  YWRtaW4=
  }
}
