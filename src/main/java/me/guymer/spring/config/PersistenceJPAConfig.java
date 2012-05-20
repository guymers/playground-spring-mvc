package me.guymer.spring.config;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Profile("jpa")
@ComponentScan("me.guymer.spring.jpa")
@EnableTransactionManagement
public class PersistenceJPAConfig {
	
	@Value("${config.domainPackageToScan}")
	private String domainPackageToScan;
	
	@Value("${jpa.propertiesFileLocation}")
	private String propertiesFileLocation;
	
	@Inject
	private DataSource dataSource;
	
	private EntityManagerFactory entityManagerFactory() throws IOException {
		return entityManagerFactoryBean().getObject();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws IOException {
		final Properties jpaProperties = jpaProperties();
		
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPackagesToScan(domainPackageToScan);
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		
		return entityManagerFactoryBean;
	}
	
	private Properties jpaProperties() throws IOException {
		final Resource location = new ClassPathResource(propertiesFileLocation);
		
		final Properties jpaProperties = new Properties();
		jpaProperties.load(location.getInputStream());
		
		return jpaProperties;
	}
	
	@Bean
	public JpaTransactionManager transactionManager() throws Exception {
		final EntityManagerFactory emf = entityManagerFactory();
		
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		
		return transactionManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		final PersistenceExceptionTranslationPostProcessor exceptionTranslation = new PersistenceExceptionTranslationPostProcessor();
		
		return exceptionTranslation;
	}

}
