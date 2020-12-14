package ru.innopolis.stc.springbootexample.security;

import java.util.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Base64PassEncoder implements PasswordEncoder {

  @Override
  public String encode(CharSequence charSequence) {
    return Base64.getEncoder().encodeToString(charSequence.toString().getBytes());
  }

  @Override
  public boolean matches(CharSequence charSequence, String s) {
    return encode(charSequence).equals(s);
  }
}
