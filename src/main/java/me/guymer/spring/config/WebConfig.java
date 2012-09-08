package me.guymer.spring.config;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired(required = false)
	private List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers;
	
	@Autowired(required = false)
	private List<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers;
	
	@PostConstruct
	public void init() {
		if (handlerMethodArgumentResolvers == null) {
			handlerMethodArgumentResolvers = Collections.emptyList();
		}
		
		if (handlerMethodReturnValueHandlers == null) {
			handlerMethodReturnValueHandlers = Collections.emptyList();
		}
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/", "classpath:/META-INF/web-resources/");
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.addAll(handlerMethodArgumentResolvers);
	}
	
	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		returnValueHandlers.addAll(handlerMethodReturnValueHandlers);
	}
	
	@Bean
	public ViewResolver internalResourceViewResolver() {
		final InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		
		return internalResourceViewResolver;
	}
	
	// seems to be required for @ControllerAdvice to work
	@Bean
	public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {
		return new ExceptionHandlerExceptionResolver();
	}
	
}
