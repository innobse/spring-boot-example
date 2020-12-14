package ru.innopolis.stc.springbootexample.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import ru.innopolis.stc.springbootexample.repositories.UserDao;
import ru.innopolis.stc.springbootexample.repositories.entities.User;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

  private final UserDao userRepo;

  public AuthSuccessHandler(UserDao userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
    String targetUrl = null;
    if (savedRequest != null) {
      //получаем урл, на который хотел попасть пользователь до авторизации
      targetUrl = savedRequest.getRedirectUrl();
    }

    clearSession(request);
    User user = null;
    try {
      user = userRepo.getUserByLogin(authentication.getName());
      if (user == null) throw new UsernameNotFoundException("Пользователь не найден");
    } catch (Exception e) {
      response.sendRedirect(request.getContextPath() + "/login");
      return;
    }

    request.getSession().setAttribute("user", user);

    if (targetUrl != null) {
      response.sendRedirect(targetUrl);
    } else {
      response.sendRedirect(request.getContextPath() + "/user");
    }
  }

  private void clearSession(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return;
    }
    session.invalidate();
  }
}
