package me.guymer.spring.config.persistence;

import java.io.IOException;
import java.util.List;
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
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

@Jpa
@Configuration
public class PersistenceJpaConfig {

	@Value("${jpa.domainPackageToScan}")
	private String jpaDomainPackageToScan;

	@Value("${jpa.propertiesFileLocation}")
	private String propertiesFileLocation;

	@Value("${jpa.databasePlatform}")
	private String databasePlatform;

	@Inject
	private DataSource dataSource;

	@Inject
	private List<PersistenceUnitPostProcessor> postProcessors;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws IOException {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(jpaDomainPackageToScan);
		entityManagerFactoryBean.setJpaProperties(jpaProperties());
		entityManagerFactoryBean.setPersistenceUnitPostProcessors(postProcessors.toArray(new PersistenceUnitPostProcessor[postProcessors.size()]));

		return entityManagerFactoryBean;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		final EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
		adapter.setDatabasePlatform(databasePlatform);

		return adapter;
	}

	private Properties jpaProperties() throws IOException {
		final Resource location = new ClassPathResource(propertiesFileLocation);

		final Properties jpaProperties = new Properties();
		jpaProperties.load(location.getInputStream());

		return jpaProperties;
	}
}
