package com.neightec.neightecavserver.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.jdbc.PreferQueryMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

//@Getter
//@Setter
@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.neightec.neightecavserver.repositories",
//        entityManagerFactoryRef = "postgresEntityManagerFactory",
//        transactionManagerRef = "postgresTransactionManager")
@Log4j2
public class NeightecDataSourceConfig {

    @Autowired
    private PrimaryDataSourceConfig config;

    @Bean
    public HikariDataSource hikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setSchema(config.getSchema());
        hikariConfig.setDriverClassName(config.getDriverClassName());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setMaxLifetime(1800000);
        hikariConfig.setLeakDetectionThreshold(120000);
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setMinimumIdle(5);

        Properties dataSourceProperties = new Properties();

        dataSourceProperties.setProperty("cachePrepStmts", "true");
        dataSourceProperties.setProperty("rewriteBatchedStatements", "true");
        dataSourceProperties.setProperty("currentSchema", config.getSchema());

        hikariConfig.setDataSourceProperties(dataSourceProperties);

        return new HikariDataSource(hikariConfig);

    }
//    @Bean
//    @Primary
//    public DataSource primaryDataSource() {
//        HikariConfig hikariConfig = new HikariConfig(config.getPrimary());
//        log.info(config.toString());
//        log.debug("configuration set: " + config.getServerName() + " - " + config.getPortNumber() + " - " + config.getDatabaseName());
//        hikariConfig.addDataSourceProperty("serverName", config.getServerName());
//        hikariConfig.addDataSourceProperty("portNumber", config.getPortNumber());
//        hikariConfig.addDataSourceProperty("databaseName", config.getDatabaseName());
//        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
//        if (hikariDataSource.getDataSource() != null) {
//            if (hikariDataSource.getDataSource() instanceof PGSimpleDataSource) {
//                PGSimpleDataSource hikariDataSourceDataSource = (PGSimpleDataSource) hikariDataSource.getDataSource();
//                hikariDataSourceDataSource.setTcpKeepAlive(true);
//                hikariDataSourceDataSource.setPreferQueryMode(PreferQueryMode.SIMPLE);
//            }
//        }
//        return hikariDataSource;
//    }

//    @Primary
//    @Bean(name = "postgresEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//        return builder
//                .dataSource(primaryDataSource())
//                .packages("com.neightec.neightecavserver.models")
//                .build();
//    }
//
//    @Primary
//    @Bean
//    public PlatformTransactionManager postgresTransactionManager(@Qualifier("postgresEntityManagerFactory") LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory) {
//        return new JpaTransactionManager(postgresEntityManagerFactory.getObject());
//    }
}
