package me.guymer.spring.config.persistence;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import me.guymer.spring.config.profile.MyBatis;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@MyBatis
@Configuration
@MapperScan("me.guymer.spring.mybatis")
public class PersistenceMyBatisConfig {

	@Value("${config.schema}")
	private String schema;

	@Value("${mybatis.domainPackageToScan}")
	private String mybatisDomainPackageToScan;

	@Value("${mybatis.configFileLocation}")
	private String mybatisConfigFileLocation;

	@Inject
	private DataSource dataSource;

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() {
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage(mybatisDomainPackageToScan);

		final Resource configLocation = new ClassPathResource(mybatisConfigFileLocation);
		sqlSessionFactoryBean.setConfigLocation(configLocation);

		final Properties configurationProperties = new Properties();
		configurationProperties.setProperty("schema", schema);

		sqlSessionFactoryBean.setConfigurationProperties(configurationProperties);

		return sqlSessionFactoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
}
