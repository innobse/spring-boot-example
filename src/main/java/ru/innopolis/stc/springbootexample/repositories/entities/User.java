package ru.innopolis.stc.springbootexample.repositories.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Entity(name = "Users")
public class User {

  @Id
  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  @Column(name = "roles")
  private String[] roles;

  public User(String login, String password, String ... roles) {
    this.login = login;
    this.password = password;
    this.roles = roles;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String[] getRoles() {
    return roles;
  }

  public void setRoles(String[] role) {
    this.roles = role;
  }
}
