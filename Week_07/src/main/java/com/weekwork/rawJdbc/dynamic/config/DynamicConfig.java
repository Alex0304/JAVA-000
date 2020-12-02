package com.weekwork.rawJdbc.dynamic.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.weekwork.rawJdbc.dynamic.datasource.DynamicDatasource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:/jdbc-dynamic-datasource.yml", encoding = "UTF-8")
public class DynamicConfig {

    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier("masterDataSource") DataSource masterDataSource, @Qualifier("slaveDatasource") DataSource slaveDatasource) {
        DynamicDatasource dynamicDatasource = new DynamicDatasource();
        return dynamicDatasource.setMasterDatasource(masterDataSource).setSlaveDatasource(slaveDatasource);
    }

    @Bean(name = "masterDataSource")
    @ConfigurationProperties("master.datasource")
    public DataSource masterDataSource() {
        return
                DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "slaveDatasource")
    @ConfigurationProperties("slave.datasource")
    public DataSource slaveDatasource() {
        return
                DruidDataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
