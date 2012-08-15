package me.guymer.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.addListener(ContextLoaderListener.class);
		servletContext.setInitParameter("contextConfigLocation", "/WEB-INF/spring/root-context.xml");
		servletContext.setInitParameter("contextInitializerClasses", "me.guymer.spring.MyApplicationContextInitializer");
		
		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
		appContext.setConfigLocation("/WEB-INF/spring/servlet-context.xml");
		
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("appServlet", new DispatcherServlet(appContext));
		dispatcher.setLoadOnStartup(0);
		dispatcher.addMapping("/");
		//dispatcher.setInitParameter("spring.profiles.active", "dev");
	}
	
}
