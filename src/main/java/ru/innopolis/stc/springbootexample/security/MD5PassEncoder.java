package ru.innopolis.stc.springbootexample.security;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MD5PassEncoder implements PasswordEncoder {

  @Override
  public String encode(CharSequence charSequence) {
    return MD5Encoder.encode(charSequence.toString().getBytes());
  }

  @Override
  public boolean matches(CharSequence charSequence, String s) {
    return encode(charSequence).equals(s);
  }
}
