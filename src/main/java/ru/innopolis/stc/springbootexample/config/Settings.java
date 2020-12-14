package ru.innopolis.stc.springbootexample.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Getter
@EnableConfigurationProperties
public class Settings {

  @Value("${myapp.config.ololo}")
  private int prop;

  @Component
  @ConfigurationProperties(prefix="myapp.config.filters")
  @Getter
  @Setter
  public static class Filters {
    List<String> nameFilters;
    int filterNum;
  }

}