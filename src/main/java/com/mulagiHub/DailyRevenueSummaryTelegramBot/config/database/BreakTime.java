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
@EnableJpaRepositories(basePackages = "com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.breaktime",
        entityManagerFactoryRef = "breaktimeEntityManagerFactory",
        transactionManagerRef= "breaktimeTransactionManager")
public class BreakTime {
    @Bean
    @ConfigurationProperties("spring.datasource.breaktime")
    public DataSourceProperties breaktimeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.breaktime.configuration")
    public DataSource breaktimeDataSource() {
        return breaktimeDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "breaktimeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean breaktimeEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(breaktimeDataSource())
                .packages("com.mulagiHub.DailyRevenueSummaryTelegramBot.models.breaktime")
                .build();
    }
    @Bean
    public PlatformTransactionManager breaktimeTransactionManager(
            final @Qualifier("breaktimeEntityManagerFactory") LocalContainerEntityManagerFactoryBean breaktimeEntityManagerFactory) {
        return new JpaTransactionManager(breaktimeEntityManagerFactory.getObject());
    }
}
