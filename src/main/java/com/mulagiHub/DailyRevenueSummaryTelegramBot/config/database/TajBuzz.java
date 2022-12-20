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
@EnableJpaRepositories(basePackages = "com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.Tajbuzz",
        entityManagerFactoryRef = "tajbuzzEntityManagerFactory",
        transactionManagerRef= "tajbuzzTransactionManager")
public class TajBuzz {
    @Bean
    @ConfigurationProperties("spring.datasource.tajbuzz")
    public DataSourceProperties tajbuzzDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.tajbuzz.configuration")
    public DataSource tajbuzzDataSource() {
        return tajbuzzDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "tajbuzzEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean tajbuzzEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(tajbuzzDataSource())
                .packages("com.mulagiHub.DailyRevenueSummaryTelegramBot.models.tajbuz")
                .build();
    }
    @Bean
    public PlatformTransactionManager tajbuzzTransactionManager(
            final @Qualifier("tajbuzzEntityManagerFactory") LocalContainerEntityManagerFactoryBean tajbuzzEntityManagerFactory) {
        return new JpaTransactionManager(tajbuzzEntityManagerFactory.getObject());
    }
}
