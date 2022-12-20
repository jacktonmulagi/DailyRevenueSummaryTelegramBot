//package com.mulagiHub.DailyRevenueSummaryTelegramBot.config.database;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJpaRepositories(basePackages = "com.africom.betmanager.repository.sportData",
//        entityManagerFactoryRef = "sportDataEntityManagerFactory",
//        transactionManagerRef= "sportDataTransactionManager")
//public class SportDataAggregatorDataSourceConfiguration {
//    @Bean
//    @ConfigurationProperties("spring.datasource.sport")
//    public DataSourceProperties sportDataDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @ConfigurationProperties("spring.datasource.sport.configuration")
//    public DataSource sportDataDataSource() {
//        return sportDataDataSourceProperties().initializeDataSourceBuilder()
//                .type(HikariDataSource.class).build();
//    }
//
//    @Bean(name = "sportDataEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean sportDataEntityManagerFactory(
//            EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(sportDataDataSource())
//                .packages("com.africom.betmanager.models.sportData")
//                .build();
//    }
//    @Bean
//    public PlatformTransactionManager sportDataTransactionManager(
//            final @Qualifier("sportDataEntityManagerFactory") LocalContainerEntityManagerFactoryBean sportDataEntityManagerFactory) {
//        return new JpaTransactionManager(sportDataEntityManagerFactory.getObject());
//    }
//}
