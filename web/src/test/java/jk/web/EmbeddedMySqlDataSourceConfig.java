package jk.web;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import jk.web.database.EmbeddedMysqlDataSource;

import org.hibernate.dialect.MySQLDialect;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.jdbc.Driver;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@PropertySources(value = {@PropertySource("classpath:application.properties")})
@EnableTransactionManagement
@EnableJpaRepositories
public class EmbeddedMySqlDataSourceConfig{

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory( entityManagerFactory );
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource( mySqlDataSource() );
//        localContainerEntityManagerFactoryBean.setPackagesToScan(new String[] { "com.touchcorp.touchppoint.model" });

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        localContainerEntityManagerFactoryBean.setJpaProperties( buildHibernateProperties() );

       return localContainerEntityManagerFactoryBean;
    }

	@Bean(destroyMethod="shutdown")
	public DataSource mySqlDataSource() {
		return EmbeddedMysqlDataSource.getInstance();
	}

	public Properties buildHibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", MySQLDialect.class.getName());
		hibernateProperties.setProperty("hibernate.connection.driver_class", Driver.class.getName());
		hibernateProperties.setProperty("hibernate.connection.url", ((EmbeddedMysqlDataSource)mySqlDataSource()).getEmbeddedUrl());
		hibernateProperties.setProperty("hibernate.connection.username", "root");
		hibernateProperties.setProperty("hibernate.connection.password", "");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
		hibernateProperties.setProperty("hibernate.hbm2ddl.import_files", "mysql-schema.sql, mysql-data.sql");
		return hibernateProperties;
	}
}
