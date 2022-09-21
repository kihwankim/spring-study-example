package com.example.jooq.config;


import org.jetbrains.annotations.NotNull;
import org.junit.ClassRule;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;

@JooqTest
@ContextConfiguration(initializers = AbstractTest.Initializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractTest {

    @ClassRule
    public static MySQLContainer mysql = new MySQLContainer("8.0.29").withDatabaseName("test");


    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
            mysql.start();
            configurableApplicationContext.getEnvironment().getSystemProperties().put("spring.datasource.url", mysql.getJdbcUrl() + "?useSSL=false");
            configurableApplicationContext.getEnvironment().getSystemProperties().put("spring.datasource.username", mysql.getUsername());
            configurableApplicationContext.getEnvironment().getSystemProperties().put("spring.datasource.password", mysql.getPassword());
        }
    }
}
