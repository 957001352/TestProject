package com.dhlk.hive.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 版本        修改时间        作者      修改内容
 * V1.0        ------        jpdong     原始版本
 * 文件说明: 数据仓库连接池
 **/
@Configuration
@AutoConfigureAfter({HiveProperties.class})
public class HiveDruidConfig {
    @Autowired
    private HiveProperties hiveConfig;

    @Bean(name = "hiveDruidDataSource")
    @Qualifier("hiveDruidDataSource")
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(hiveConfig.getUrl());
        datasource.setUsername(hiveConfig.getUser());
        datasource.setPassword(hiveConfig.getPassword());
        datasource.setDriverClassName(hiveConfig.getDriverClassName());

        // pool configuration
        datasource.setInitialSize(hiveConfig.getInitialSize());
        datasource.setMinIdle(hiveConfig.getMinIdle());
        datasource.setMaxActive(hiveConfig.getMaxActive());
        datasource.setMaxWait(hiveConfig.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(hiveConfig.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(hiveConfig.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(hiveConfig.getValidationQuery());
        datasource.setTestWhileIdle(hiveConfig.isTestWhileIdle());
        datasource.setTestOnBorrow(hiveConfig.isTestOnBorrow());
        datasource.setTestOnReturn(hiveConfig.isTestOnReturn());
        datasource.setPoolPreparedStatements(hiveConfig.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(hiveConfig.getMaxPoolPreparedStatementPerConnectionSize());
        return datasource;
    }

    // 此处省略各个属性的get和set方法

    @Bean(name = "hiveDruidTemplate")
    public JdbcTemplate hiveDruidTemplate(@Qualifier("hiveDruidDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}



