package com.example.fallenangels.config.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.fallenangels.repository.Database1",
        entityManagerFactoryRef = "db1EntityManager",
        transactionManagerRef = "db1TransactionManager")
public class db1DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean db1EntityManager(EntityManagerFactoryBuilder builder, DataSource dataSource)
    {
        return builder
                .dataSource(dataSource)
                .packages("com.example.fallenangels.models.Database1")
                .persistenceUnit("db1EntityManager")
                .properties(hibernateProperties())
                .build();
    }

    private Map<String, Object> hibernateProperties()
    {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", env.getProperty("db1.hibernate.dialect"));
        properties.put("hibernate.show-sql", env.getProperty("db1.jdbc.show-sql"));
        return properties;
    }

    @Primary
    @Bean
    public DataSource db1Datasource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("db1.jdbc.driver-class-name")));
        dataSource.setUrl(env.getProperty("db1.datasource.url"));
        dataSource.setUsername(env.getProperty("db1.datasource.username"));
        dataSource.setPassword(env.getProperty("db1.datasource.password"));
        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager db1TransactionManager(EntityManagerFactory entityManagerFactory)
    {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
