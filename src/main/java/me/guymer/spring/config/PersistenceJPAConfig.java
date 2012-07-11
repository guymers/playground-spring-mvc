package me.guymer.spring.config;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class PersistenceJPAConfig {
	
	@Value("${jpa.domainPackageToScan}")
	private String jpaDomainPackageToScan;
	
	@Value("${jpa.propertiesFileLocation}")
	private String propertiesFileLocation;
	
	@Inject
	private DataSource dataSource;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws IOException {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan(jpaDomainPackageToScan);
		
		final Properties jpaProperties = jpaProperties();
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
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		final PersistenceExceptionTranslationPostProcessor exceptionTranslation = new PersistenceExceptionTranslationPostProcessor();
		
		return exceptionTranslation;
	}

}
