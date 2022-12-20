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
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.africom",
        entityManagerFactoryRef = "africomEntityManagerFactory",
        transactionManagerRef= "africomTransactionManager")
public class Africom {

    @Bean
    @ConfigurationProperties("spring.datasource.africom")
    public DataSourceProperties africomDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.africom.configuration")
    public DataSource africomDataSource() {
        return africomDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "africomEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean africomEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        Map<String, String> africomJpaProperties = new HashMap<>();
        africomJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        africomJpaProperties.put("hibernate.hbm2ddl.auto", "none");
        return builder
                .dataSource(africomDataSource())
                .packages("com.mulagiHub.DailyRevenueSummaryTelegramBot.models.africom")
                .properties(africomJpaProperties)
                .build();
    }

    @Bean
    public PlatformTransactionManager africomTransactionManager(
            final @Qualifier("africomEntityManagerFactory") LocalContainerEntityManagerFactoryBean africomEntityManagerFactory) {
        return new JpaTransactionManager(africomEntityManagerFactory.getObject());
    }

}

