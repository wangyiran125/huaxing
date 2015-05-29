package org.chinalbs.logistics.config;

import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
public class BoneCPDataSourceConfig implements DataSourceConfig {
    @Value("${db.driver}")
    private String driver;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.minConnections}")
    private int minConnections;
    @Value("${db.maxConnections}")
    private int maxConnections;
    @Value("${db.connectionTimeout}")
    private int connectionTimeout;
    @Value("${db.acquireIncrement}")
    private int acquireIncrement;
	
	@Bean
	public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDefaultAutoCommit(false);
        dataSource.setMinConnectionsPerPartition(minConnections);
        dataSource.setMaxConnectionsPerPartition(maxConnections);
        dataSource.setConnectionTimeout(connectionTimeout, TimeUnit.SECONDS);
        dataSource.setAcquireIncrement(acquireIncrement);

        return dataSource;
	}

}
