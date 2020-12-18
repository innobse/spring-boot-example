package ru.innopolis.stc.springbootexample.repositories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.innopolis.stc.springbootexample.repositories.entities.User;

public class SpringDataUserDao implements UserDao {

  private final UserRepository repository;
  private final PasswordEncoder encoder;

  public SpringDataUserDao(UserRepository repository, PasswordEncoder encoder) {
    this.repository = repository;
    this.encoder = encoder;
  }

  @Override
  public User addUser(User user) {
    return repository.save(user);
  }

  @Override
  public User getUserByLogin(String userLogin) {
    return repository.getOne(userLogin);
  }

  @Override
  public Collection<User> getAll() {
    return repository.findAll();
  }

  @PostConstruct
  public void init() {
    addUser(
        new User("admin", encoder.encode("admin"), "ROLE_USER", "ROLE_ADMIN")
    );
    addUser(
        new User("user", encoder.encode("user"), "ROLE_USER")
    );
  }
}
