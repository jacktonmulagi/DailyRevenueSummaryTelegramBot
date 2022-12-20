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
@EnableJpaRepositories(basePackages = "com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.zuree",
        entityManagerFactoryRef = "zureeEntityManagerFactory",
        transactionManagerRef= "zureeTransactionManager")
public class Zuree {
    @Bean
    @ConfigurationProperties("spring.datasource.zuree")
    public DataSourceProperties zureeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.zuree.configuration")
    public DataSource zureeDataSource() {
        return zureeDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "zureeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean zureeEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(zureeDataSource())
                .packages("com.mulagiHub.DailyRevenueSummaryTelegramBot.models.zuree")
                .build();
    }
    @Bean
    public PlatformTransactionManager zureeTransactionManager(
            final @Qualifier("zureeEntityManagerFactory") LocalContainerEntityManagerFactoryBean zureeEntityManagerFactory) {
        return new JpaTransactionManager(zureeEntityManagerFactory.getObject());
    }
}
