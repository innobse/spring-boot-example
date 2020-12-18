package ru.innopolis.stc.springbootexample.repository;

import java.io.IOException;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

public class ContextInitializer implements
    ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(GenericApplicationContext applicationContext) {
      EmbeddedPostgres postgres = new EmbeddedPostgres();
      try {
        String url = postgres.start();
        TestPropertyValues values = TestPropertyValues.of(
            "spring.test.database.replace=none",
            "spring.datasource.url=" + url,
            "spring.datasource.driver-class-name=org.postgresql.Driver",
            "spring.jpa.hibernate.ddl-auto=create");

        values.applyTo(applicationContext);

        applicationContext.registerBean(EmbeddedPostgres.class, () -> postgres,
            beanDefinition -> beanDefinition.setDestroyMethodName("stop"));
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }


}