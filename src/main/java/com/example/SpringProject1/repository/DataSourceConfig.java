package com.example.SpringProject1.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final DataSource dataSource;

    public Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

}
