package me.guymer.spring.config.persistence;

import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import me.guymer.spring.config.profile.Jpa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Jpa
@Configuration
public class PersistenceJpaConfig {

	private static final String HIBERNATE_DIALECT = "hibernate.dialect";

	@Value("${jpa.domainPackageToScan}")
	private String jpaDomainPackageToScan;

	@Value("${jpa.propertiesFileLocation}")
	private String propertiesFileLocation;

	@Value("${" + HIBERNATE_DIALECT + "}")
	private String hibernateDialect;

	@Inject
	private DataSource dataSource;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws IOException {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(jpaDomainPackageToScan);
		entityManagerFactoryBean.setJpaProperties(jpaProperties());

		return entityManagerFactoryBean;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	private Properties jpaProperties() throws IOException {
		final Resource location = new ClassPathResource(propertiesFileLocation);

		final Properties jpaProperties = new Properties();
		jpaProperties.load(location.getInputStream());

		jpaProperties.setProperty(HIBERNATE_DIALECT, hibernateDialect);

		return jpaProperties;
	}
}
