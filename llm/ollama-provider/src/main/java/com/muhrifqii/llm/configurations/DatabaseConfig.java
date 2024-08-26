package com.muhrifqii.llm.configurations;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableConfigurationProperties({ R2dbcProperties.class, FlywayProperties.class })
@Slf4j
public class DatabaseConfig {
    @Bean(initMethod = "migrate")
    Flyway flyway(FlywayProperties flywayProperties, R2dbcProperties r2dbcProperties) {
        log.info("Flyway: {} {} {}", flywayProperties.getUrl(), r2dbcProperties.getUsername(),
                r2dbcProperties.getPassword());
        return Flyway.configure()
                .dataSource(flywayProperties.getUrl(), r2dbcProperties.getUsername(), r2dbcProperties.getPassword())
                .locations(flywayProperties.getLocations().toArray(String[]::new))
                .baselineOnMigrate(true)
                .validateOnMigrate(true)
                .load();
    }
}
