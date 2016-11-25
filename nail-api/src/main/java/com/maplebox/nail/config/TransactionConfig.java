package com.maplebox.nail.config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.sql.DataSource;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import net.bull.javamelody.JdbcDriver;

@Configuration
@ComponentScan(basePackages = {"com.maplebox.nail.service"})
@PropertySource(
	value = {"classpath:config/web-config-${web.mode:test}.xml"},
	ignoreResourceNotFound = false
)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
@EnableJpaRepositories(
	basePackages = "com.maplebox.nail.repository",
	entityManagerFactoryRef = "entityManagerFactory",
	transactionManagerRef = "transactionManager"
)
public class TransactionConfig {
	static Logger log = LoggerFactory.getLogger(TransactionConfig.class);
	
	@Value("${db.url}")
	private String url;
	
	@Value("${db.dbname}")
	private String dbname;
	
	@Value("${db.username}")
	private String username;
	
	@Value("${db.password}")
	private String password;
	
	@Value("${db.showSql}")
	private Boolean showSql;
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource, JpaVendorAdapter jpaVendorAdapter,
			PersistenceProvider persistenceProvider) {
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(dataSource);
		bean.setJpaVendorAdapter(jpaVendorAdapter);
		bean.setPersistenceProvider(persistenceProvider);
		bean.setJpaPropertyMap(jpaPropertyMap());
		bean.setPackagesToScan(new String[] {"com.maplebox.nail.table"});
		return bean;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		transactionManager.setDefaultTimeout(20);
		return transactionManager;
	}
	 
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
		bean.setShowSql(showSql);
		bean.setGenerateDdl(false);
		bean.setDatabase(Database.MYSQL);
		bean.setDatabasePlatform(MySQL5InnoDBDialect.class.getName());
		return bean;
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setInitialSize(1);
		dataSource.setMinIdle(5);
		dataSource.setMaxIdle(60);
		dataSource.setMaxActive(60);
		dataSource.setMaxWait(3000);
		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		dataSource.setNumTestsPerEvictionRun(60);
		dataSource.setMinEvictableIdleTimeMillis(18000000);
		dataSource.setRemoveAbandonedTimeout(180);
		dataSource.setLogAbandoned(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setValidationQuery("SELECT 1");
		dataSource.setValidationQueryTimeout(3);
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}
	
	@Bean
	public PersistenceProvider persistenceProvider() {
		return new HibernatePersistenceProvider();
	}
	
	private Map<String, Object> jpaPropertyMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("hibernate.dialect", MySQL5Dialect.class);
		map.put("hibernate.show_sql", showSql);
		map.put("hibernate.format_sql", showSql);
		map.put("hibernate.use_sql_comments", false);
		map.put("hibernate.default_catalog", dbname);
		map.put("hibernate.hbm2ddl.auto", "validate");
		map.put("hibernate.connection.driver_class", JdbcDriver.class);
		map.put("hibernate.connection.driver", com.mysql.jdbc.Driver.class);
		return map;
	}
	
	@PreDestroy
    public void contextDestroyed() {
		log.info("TransactionConfig contextDestroyed");
		Enumeration<Driver> drivers = DriverManager.getDrivers();     

        Driver driver = null;

        while(drivers.hasMoreElements()) {
        	try {
                driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
            	log.error("exp", e);
            }
        }

        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
        	log.error("exp", e);
        }
    }
}
