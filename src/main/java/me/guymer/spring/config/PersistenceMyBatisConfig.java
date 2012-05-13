package me.guymer.spring.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Profile("mybatis")
@ComponentScan("me.guymer.spring.mybatis")
@EnableTransactionManagement
public class PersistenceMyBatisConfig {
	
	@Value("${config.schema}")
	private String schema;
	
	@Value("${mybatis.domainPackageToScan}")
	private String domainPackageToScan;
	
	@Inject
	private DataSource dataSource;
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		
		return transactionManager;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		final SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		
		final org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setUseGeneratedKeys(true);
		configuration.setDefaultExecutorType(ExecutorType.REUSE);
		
		Properties variables = configuration.getVariables();
		
		if (variables == null) {
			variables = new Properties();
			
			configuration.setVariables(variables);
		}
		
		variables.put("schema", schema);
		
		return sqlSessionFactory;
	}
	
	private SqlSessionFactory getSqlSessionFactory() throws Exception {
        return sqlSessionFactoryBean().getObject();
    }
	
	//@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage(domainPackageToScan);
		sqlSessionFactoryBean.afterPropertiesSet();
		
		return sqlSessionFactoryBean;
	}
	
	/*@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		final MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("me.guymer.spring.mybatis.persistence");
		//mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		
		return mapperScannerConfigurer;
	}*/
	
}
