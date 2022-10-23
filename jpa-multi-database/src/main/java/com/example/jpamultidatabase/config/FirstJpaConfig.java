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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.jpamultidatabase.first",
        entityManagerFactoryRef = "firstEntityManager",
        transactionManagerRef = "firstTransactionManager"
)
@RequiredArgsConstructor
public class FirstJpaConfig {
    //    private final JpaPropertiesConfig jpaPropertiesConfig;
    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;

    @Bean
    @ConfigurationProperties(prefix = "spring.first.datasource.hikari")
    public HikariConfig firstHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Primary
    public HikariDataSource firstDataSource() {
        return new HikariDataSource(firstHikariConfig());
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean firstEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(firstDataSource());
        em.setPackagesToScan("com.example.jpamultidatabase.first");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> stringObjectMap = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings()
        );

        em.setJpaPropertyMap(stringObjectMap);


        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager firstTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(firstEntityManager().getObject());
        return transactionManager;
    }
}
