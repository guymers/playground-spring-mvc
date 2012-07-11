package me.guymer.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	
	@Override
	public void initialize(ConfigurableApplicationContext context) {
		ConfigurableEnvironment env = context.getEnvironment();
		env.setActiveProfiles("dev");
	}
	
}
