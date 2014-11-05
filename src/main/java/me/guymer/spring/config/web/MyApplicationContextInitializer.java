package me.guymer.spring.config.web;

import me.guymer.spring.config.profile.Profiles;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext context) {
		ConfigurableEnvironment env = context.getEnvironment();
		env.addActiveProfile(Profiles.WEB);
		env.addActiveProfile(Profiles.DEV);
		//env.addActiveProfile(Profiles.MYBATIS);
		env.addActiveProfile(Profiles.JPA);
	}
}
