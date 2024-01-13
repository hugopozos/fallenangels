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
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.fallenangels.repository.Database2",
        entityManagerFactoryRef = "db2EntityManager",
        transactionManagerRef = "db2TransactionManager")
public class db2DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean db2EntityManager(EntityManagerFactoryBuilder builder, DataSource dataSource)
    {
        return builder
                .dataSource(dataSource)
                .packages("com.example.fallenangels.models.Database2")
                .persistenceUnit("db2EntityManager")
                .properties(hibernateProperties())
                .build();
    }

    @Bean
    public EntityManagerFactoryBuilder.Builder db2EntityManagerFactoryBuilder(EntityManagerFactoryBuilder builder)
    {
        return builder
                .dataSource(db2Datasource())
                .packages("com.example.fallenangels.models.Database2")
                .persistenceUnit("db2EntityManager")
                .properties(hibernateProperties());
    }

    private Map<String, Object> hibernateProperties()
    {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", env.getProperty("db2.hibernate.dialect"));
        properties.put("hibernate.show-sql", env.getProperty("db2.jdbc.show-sql"));
        return properties;
    }

    @Bean
    public DataSource db2Datasource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("db2.jdbc.driver-class-name")));
        dataSource.setUrl(env.getProperty("db2.datasource.url"));
        dataSource.setUsername(env.getProperty("db2.datasource.username"));
        dataSource.setPassword(env.getProperty("db2.datasource.password"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager db2TransactionManager(EntityManagerFactory entityManagerFactory)
    {
        return new JpaTransactionManager(entityManagerFactory);
    }

}

