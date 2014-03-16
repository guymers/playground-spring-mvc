package me.guymer.spring.config.web;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class MyContextLoaderListener extends ContextLoaderListener {

	public MyContextLoaderListener(WebApplicationContext context) {
		super(context);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected List<Class<ApplicationContextInitializer<ConfigurableApplicationContext>>> determineContextInitializerClasses(ServletContext servletContext) {
		List<Class<ApplicationContextInitializer<ConfigurableApplicationContext>>> classes = super.determineContextInitializerClasses(servletContext);

		Class<?> clazz = MyApplicationContextInitializer.class;
		classes.add((Class<ApplicationContextInitializer<ConfigurableApplicationContext>>)clazz);

		return classes;
	}
}
