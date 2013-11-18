package me.guymer.spring.config.datasource;

import javax.sql.DataSource;

import me.guymer.spring.config.profile.Test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Test
@PropertySource("classpath:properties/database.test.properties")
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
