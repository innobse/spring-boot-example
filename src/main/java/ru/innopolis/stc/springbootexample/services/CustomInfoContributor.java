package ru.innopolis.stc.springbootexample.services;

import org.springframework.boot.actuate.info.GitInfoContributor;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.util.Properties;

/**
 * Класс для вытягивания информации из гита
 */
@Primary
@Component
@ConditionalOnBean(name = "gitProperties")
public class CustomInfoContributor extends GitInfoContributor implements InfoContributor {

  public CustomInfoContributor(GitProperties properties) {
    super(properties);
  }

  @Override
  protected PropertySource<?> toSimplePropertySource() {
    Properties props = new Properties();
    copyIfSet(props, "branch");
    copyIfSet(props, "tags");
    String commitId = getProperties().getShortCommitId();
    if (commitId != null) {
      props.put("commit.id", commitId);
    }
    copyIfSet(props, "commit.time");
    return new PropertiesPropertySource("git", props);
  }
}
