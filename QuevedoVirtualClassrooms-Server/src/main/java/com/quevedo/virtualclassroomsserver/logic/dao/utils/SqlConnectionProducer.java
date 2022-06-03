package com.quevedo.virtualclassroomsserver.logic.dao.utils;

import com.quevedo.virtualclassroomsserver.common.config.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import javax.sql.DataSource;

public class SqlConnectionProducer {

    @Singleton
    @Produces
    public DataSource getDataSource(Config config){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getDbPath());
        hikariConfig.setUsername(config.getDbUser());
        hikariConfig.setPassword(config.getDbPass());
        hikariConfig.setDriverClassName(config.getDbDriver());
        hikariConfig.setMaximumPoolSize(1);
        hikariConfig.addDataSourceProperty(DaoConstants.CACHE_PREP_STMTS, DaoConstants.CACHE_PREP_STMTS_VALUE);
        hikariConfig.addDataSourceProperty(DaoConstants.PREP_STMT_CACHE_SIZE, DaoConstants.PREP_STMT_CACHE_SIZE_VALUE);
        hikariConfig.addDataSourceProperty(DaoConstants.PREP_STMT_CACHE_SQL_LIMIT, DaoConstants.PREP_STMT_CACHE_SQL_LIMIT_VALUE);
        return new HikariDataSource(hikariConfig);
    }
}
