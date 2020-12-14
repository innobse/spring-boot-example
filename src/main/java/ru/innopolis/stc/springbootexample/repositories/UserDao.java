package ru.innopolis.stc.springbootexample.repositories;

import java.util.Collection;
import ru.innopolis.stc.springbootexample.repositories.entities.User;

public interface UserDao {

  User addUser(User user);
  User getUserByLogin(String userLogin);
  Collection<User> getAll();
}
