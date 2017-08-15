package com.employmeo.data.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:database.properties" })
@ComponentScan(basePackages = "com.employmeo.data")
@EnableJpaRepositories(basePackages = "com.employmeo.data")
@Slf4j
public class PersistenceConfiguration {

	@Autowired
	private Environment environment;

	/*
	 * removed following code to replace with pooled data connection.
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}
   */
	
	@Bean
	public ComboPooledDataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();  
        try {
        	dataSource.setDriverClass(environment.getRequiredProperty("jdbc.driverClassName"));
        } catch (PropertyVetoException pve){
            log.error("Cannot load datasource driver {} with error {}", environment.getRequiredProperty("jdbc.driverClassName"), pve.getMessage());
            return null;
        }
		dataSource.setJdbcUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUser(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		dataSource.setMinPoolSize(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.min_size")));
		dataSource.setMaxPoolSize(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.max_size")));
		dataSource.setMaxIdleTime(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.timeout")));
		dataSource.setMaxStatements(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.max_statements")));
		dataSource.setIdleConnectionTestPeriod(Integer.parseInt(environment.getRequiredProperty("hibernate.c3p0.idle_test_period")));	
        return dataSource;
    }
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] { "com.employmeo.data.model" });
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaProperties(jpaProperties());
		return factoryBean;
	}

	/*
	 * Provider specific adapter.
	 */
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		return hibernateJpaVendorAdapter;
	}

	/*
	 * Here you can specify any provider specific properties.
	 */
	private Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
			
		return properties;
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

}
