package com.example.jpamultidatabase.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@PropertySource({ "classpath:application.yml" })
@EnableJpaRepositories(
        basePackages = "com.example.jpamultidatabase.second",
        entityManagerFactoryRef = "secondEntityManager",
        transactionManagerRef = "secondTransactionManager"
)
@RequiredArgsConstructor
public class SecondJpaConfig {

    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;


    @Bean
    @ConfigurationProperties(prefix = "spring.second.datasource.hikari")
    public HikariConfig secondHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public HikariDataSource secondDataSource() {
        return new HikariDataSource(secondHikariConfig());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean secondEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(secondDataSource());
        em.setPackagesToScan("com.example.jpamultidatabase.second");

        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> stringObjectMap = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings()
        );

        em.setJpaPropertyMap(stringObjectMap);

        return em;
    }

    @Bean
    public PlatformTransactionManager secondTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(secondEntityManager().getObject());
        return transactionManager;
    }
}
