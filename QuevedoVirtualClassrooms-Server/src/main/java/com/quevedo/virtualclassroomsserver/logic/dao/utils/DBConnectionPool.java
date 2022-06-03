package com.quevedo.virtualclassroomsserver.logic.dao.utils;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
@Getter
@Singleton
public class DBConnectionPool {
    private final DataSource hikariDatasource;

    @Inject
    public DBConnectionPool(DataSource hikariDatasource){
        this.hikariDatasource = hikariDatasource;
    }

    public void closeConnection(Connection connection){
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException sqlException) {
            log.error(sqlException.getMessage(), sqlException);
        }
    }

    @Produces
    public JdbcTemplate produceJdbcTemplate() {
        return new JdbcTemplate(hikariDatasource);
    }

    @PreDestroy
    public void closePool() {
        ((HikariDataSource) hikariDatasource).close();
    }
}
