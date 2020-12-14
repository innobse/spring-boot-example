package ru.innopolis.stc.springbootexample.controllers;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.stc.springbootexample.repositories.entities.User;
import ru.innopolis.stc.springbootexample.dto.UserDto;
import ru.innopolis.stc.springbootexample.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = "/{username}", method = RequestMethod.GET)
  public UserDto getUser(@PathVariable String username) {
    final User userByUsername = userService.getUserByUsername(username);
    if (userByUsername == null) return null;
    return new UserDto(userByUsername.getLogin(), userByUsername.getRoles());
  }

  @GetMapping
  public Collection<UserDto> getUser() {
    final Collection<User> allUsers = userService.getAllUsers();
    return allUsers.stream()
        .map(user -> new UserDto(user.getLogin(), user.getRoles()))
        .collect(Collectors.toList());
  }

  @PostMapping
  public UserDto addUser(@RequestBody UserDto userDto) {
    User newUser = new User(userDto.getUsername(), "123", userDto.getRoles());
    newUser = userService.createNewUser(newUser);
    return new UserDto(newUser.getLogin(), newUser.getRoles());
  }

  @GetMapping("/getexception")
  public UserDto simulateException() {
    throw new RuntimeException("Some problem");
  }

  @ExceptionHandler
  @ResponseBody
  public ResponseEntity<String> catchException(Exception err) {
    StringBuilder sb = new StringBuilder(1024);
    sb.append("<html><body><h2>Произошла ошибка при работе с записями User</h2><p>");
    Arrays.stream(err.getStackTrace())
        .forEach(e -> sb
            .append(e.getClassName())
            .append(":")
            .append(e.getLineNumber())
            .append("\t")
            .append(e.getMethodName()));

    sb.append("</p></body></html>");
    return ResponseEntity.status(500).body(
        sb.toString()
    );
  }

}
