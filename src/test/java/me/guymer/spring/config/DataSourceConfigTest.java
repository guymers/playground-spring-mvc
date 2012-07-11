package me.guymer.spring.config;

import javax.sql.DataSource;

import me.guymer.spring.config.DataSourceConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile("test")
public class DataSourceConfigTest implements DataSourceConfig {
	
	@Bean
	@Override
	public DataSource dataSource() {
		final EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
		embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.HSQL);
		embeddedDatabaseBuilder.addScript("classpath:sql/schema.sql");
		embeddedDatabaseBuilder.addScript("classpath:sql/data.sql");
		
		return embeddedDatabaseBuilder.build();
	}
}
