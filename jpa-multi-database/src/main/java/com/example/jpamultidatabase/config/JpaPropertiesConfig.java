package com.example.jpamultidatabase.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaPropertiesConfig {

//    @Bean
//    @ConfigurationProperties(prefix = "spring.jpa")
//    public JpaProperties jpaProperties() {
//        return new JpaProperties();
//    }
//
//    @Bean
//    @ConfigurationProperties("spring.jpa.hibernate")
//    public HibernateProperties hibernateProperties() {
//        return new HibernateProperties();
//    }
}
