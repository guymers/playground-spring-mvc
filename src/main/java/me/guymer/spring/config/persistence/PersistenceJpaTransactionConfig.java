package me.guymer.spring.config.persistence;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import me.guymer.spring.config.profile.Jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Jpa
@Configuration
public class PersistenceJpaTransactionConfig {

	@Inject
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public PlatformTransactionManager transactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);

		return transactionManager;
	}
}
