package com.mulagiHub.DailyRevenueSummaryTelegramBot.config.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.digital",
        entityManagerFactoryRef = "digitalEntityManagerFactory",
        transactionManagerRef= "digitalTransactionManager")
public class DigitalOcean {
    @Bean
    @ConfigurationProperties("spring.datasource.africom1")
    public DataSourceProperties zureeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.digital.configuration")
    public DataSource zureeDataSource() {
        return zureeDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "digitalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean digitalEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(zureeDataSource())
                .packages("com.mulagiHub.DailyRevenueSummaryTelegramBot.models.digital")
                .build();
    }
    @Bean
    public PlatformTransactionManager digitalTransactionManager(
            final @Qualifier("digitalEntityManagerFactory") LocalContainerEntityManagerFactoryBean digitalEntityManagerFactory) {
        return new JpaTransactionManager(digitalEntityManagerFactory.getObject());
    }
}
