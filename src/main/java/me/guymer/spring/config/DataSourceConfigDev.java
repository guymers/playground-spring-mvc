package me.guymer.spring.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile("dev")
public class DataSourceConfigDev implements DataSourceConfig {
	
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
