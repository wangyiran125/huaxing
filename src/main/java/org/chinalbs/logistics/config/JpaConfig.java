package org.chinalbs.logistics.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.chinalbs.logistics.domain.Domain;
import org.chinalbs.logistics.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = Repository.class)
class JpaConfig implements TransactionManagementConfigurer {

    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;
    @Value("${hibernate.format_sql}")
    private boolean formatSQL;
    @Value("${hibernate.show_sql}")
    private boolean showSQL;
    @Value("${hibernate.use_sql_comments}")
	private boolean useSQLComments;

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource datasource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(datasource);
        entityManagerFactoryBean.setPackagesToScan(Domain.class.getPackage().getName());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, dialect);
        jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        jpaProperties.put(org.hibernate.cfg.Environment.FORMAT_SQL, formatSQL);
        jpaProperties.put(org.hibernate.cfg.Environment.SHOW_SQL, showSQL);
        jpaProperties.put(org.hibernate.cfg.Environment.USE_SQL_COMMENTS, useSQLComments);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean PlatformTransactionManager transactionManager() {
    	return new JpaTransactionManager();
    }
    
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }
}
