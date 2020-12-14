package ru.innopolis.stc.springbootexample.services;

import java.util.Collection;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.innopolis.stc.springbootexample.repositories.UserDao;
import ru.innopolis.stc.springbootexample.repositories.entities.User;
import ru.innopolis.stc.springbootexample.security.MyUserDetails;

public class UserService implements UserDetailsService {

  private final UserDao repository;

  public UserService(UserDao repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User userByLogin = getUserByUsername(username);
    return userByLogin == null ? null : new MyUserDetails(userByLogin);
  }

  public User getUserByUsername(String username) {
    return repository.getUserByLogin(username);
  }

  public Collection<User> getAllUsers() {
    return repository.getAll();
  }

  public User createNewUser(User newUser) {
    return repository.addUser(newUser);
  }
}
