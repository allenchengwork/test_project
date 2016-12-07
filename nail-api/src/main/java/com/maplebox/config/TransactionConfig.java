package com.maplebox.config;

import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.maplebox.app.AppInitializer;
import com.mysql.jdbc.AbandonedConnectionCleanupThread;

@Configuration
@ComponentScan(basePackages = {"com.maplebox.service"})
@ImportResource(locations = {
	"classpath:datasource/hikari-cp.xml",
	"classpath:hibernate.xml"
})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
@EnableJpaRepositories(
	basePackages = "com.maplebox.repository",
	entityManagerFactoryRef = "entityManagerFactory",
	transactionManagerRef = "transactionManager"
)
public class TransactionConfig {
	static Logger log = LoggerFactory.getLogger(TransactionConfig.class);
	
	@Bean
    public static PropertyPlaceholderConfigurer propertyConfigurer() throws IOException {
		log.debug("TransactionConfig.propertyConfigurer");
		return AppInitializer.propertyConfigurer();
    }

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		transactionManager.setDefaultTimeout(20);
		return transactionManager;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
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
