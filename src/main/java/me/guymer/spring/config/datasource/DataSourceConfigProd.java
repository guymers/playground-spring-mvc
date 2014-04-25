package me.guymer.spring.config.datasource;

import javax.sql.DataSource;

import me.guymer.spring.config.profile.Prod;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Prod
@Configuration
@PropertySource("classpath:properties/database.prod.properties")
public class DataSourceConfigProd implements DataSourceConfig {

	@Value("${database.driver}")
	private String driver;

	@Value("${database.url}")
	private String url;

	@Value("${database.username}")
	private String username;

	@Value("${database.password}")
	private String password;

	@Bean
	@Override
	public DataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(100);
		config.setDataSourceClassName(driver);
		config.addDataSourceProperty("url", url);
		config.addDataSourceProperty("user", username);
		config.addDataSourceProperty("password", password);

		return new HikariDataSource(config);
	}
}
