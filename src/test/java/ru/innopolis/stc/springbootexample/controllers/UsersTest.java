package ru.innopolis.stc.springbootexample.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.innopolis.stc.springbootexample.SpringBootExampleApplication;
import ru.innopolis.stc.springbootexample.dto.UserDto;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {SpringBootExampleApplication.class})
@ActiveProfiles({"local", "in-memory"})
class UsersTest {

  @Autowired
  private MockMvc mockMvc;

  ObjectMapper mapper = new ObjectMapper();

  @Test
  public void testUsersGetAll() throws Exception {

    final String result = performGet("/users", "user", "user", "USER");
    final UserDto[] resultUsers = mapper.readValue(result, UserDto[].class);
    assertTrue(resultUsers.length > 2);
  }

  @Test
  public void testUsersGetOne() throws Exception {

    final String result = performGet("/users/admin", "user", "user", "USER");
    final UserDto resultUser = mapper.readValue(result, UserDto.class);
    assertEquals("admin", resultUser.getUsername());
  }

  @Test
  public void testRedirectIfAnonimousUsersGet() throws Exception {

    mockMvc
        .perform(get("/users")
            .with(anonymous()))
        .andDo(print())
        .andExpect(status().is3xxRedirection());
  }

  @Test
  public void testFailIfNonUserRoleUsersGet() throws Exception {

    mockMvc
        .perform(get("/users")
            .with(user("guestTmp").password("guestTmp").roles("GUEST")))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  private String performGet(String path, String user, String password, String ... roles) throws Exception {
    return mockMvc.perform(
        get(
            path)
            .with(
                user(user).password(password).roles(roles))
            .contentType(MediaType.APPLICATION_JSON)
            .cookie(
                new Cookie("name", "value")
            )
            .headers(
                new HttpHeaders()
            )
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();
  }

}
