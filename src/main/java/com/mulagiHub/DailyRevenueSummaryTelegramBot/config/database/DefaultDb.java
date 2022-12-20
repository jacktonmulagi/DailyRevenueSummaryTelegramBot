package com.mulagiHub.DailyRevenueSummaryTelegramBot.config.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.mulagiHub.DailyRevenueSummaryTelegramBot.repository.main",
        entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef= "mainTransactionManager")
public class DefaultDb {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.main")
    public DataSourceProperties mainDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.main.configuration")
    public DataSource userDataSource() {
        return mainDatasourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "mainEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, String> mainJpaProperties = new HashMap<>();
        mainJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        mainJpaProperties.put("hibernate.hbm2ddl.auto", "none");
        return builder
                .dataSource(userDataSource())
                .packages("com.mulagiHub.DailyRevenueSummaryTelegramBot.models.main")
                .properties(mainJpaProperties)
                .build();
    }

    @Primary
    @Bean(name = "mainTransactionManager")
    public PlatformTransactionManager reportTransactionManager(
            final @Qualifier("mainEntityManagerFactory") LocalContainerEntityManagerFactoryBean mainEntityManagerFactory) {
        return new JpaTransactionManager(mainEntityManagerFactory.getObject());
    }
}
