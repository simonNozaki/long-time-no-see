package io.github.simonnozaki.longtimenosee.infrastructure;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Objects;

/**
 * データベース設定クラス
 */
@Configuration
public class DatabaseConfiguration {
    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        final var dataSource = new DriverManagerDataSource();
        final var driverClassName = Objects.requireNonNull(env.getProperty("driverClassName"));
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        return dataSource;
    }
}
