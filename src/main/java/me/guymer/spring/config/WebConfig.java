package me.guymer.spring.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import me.guymer.spring.config.profile.Web;
import me.guymer.spring.web.ext.ExtJsonReturnValueHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Web
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
	
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
		
		addExtJsonReturnValueHandler();
	}
	
	/**
	 * ExtJsonReturnValueHandler uses the default RequestResponseBodyMethodProcessor so the
	 * RequestMappingHandlerAdapter must be
	 * created first so then the RequestResponseBodyMethodProcessor can be extracted. The
	 * ExtJsonReturnValueHandler handler will
	 * not be tried if it is last element of the array so it is added to the start.
	 */
	private void addExtJsonReturnValueHandler() {
		RequestMappingHandlerAdapter requestMappingHandlerAdapter = requestMappingHandlerAdapter();
		HandlerMethodReturnValueHandlerComposite returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
		RequestResponseBodyMethodProcessor responseBodyProcessor = getRequestResponseBodyMethodProcessor(returnValueHandlers);
		
		ExtJsonReturnValueHandler extJsonHandler = new ExtJsonReturnValueHandler(responseBodyProcessor);
		
		List<HandlerMethodReturnValueHandler> handlers = returnValueHandlers.getHandlers();
		// handlers is immutable
		List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<HandlerMethodReturnValueHandler>(handlers);
		newHandlers.add(0, extJsonHandler);
		
		requestMappingHandlerAdapter.setReturnValueHandlers(newHandlers);
	}
	
	private RequestResponseBodyMethodProcessor getRequestResponseBodyMethodProcessor(HandlerMethodReturnValueHandlerComposite returnValueHandlers) {
		List<HandlerMethodReturnValueHandler> handlers = returnValueHandlers.getHandlers();
		
		RequestResponseBodyMethodProcessor responseBodyProcessor = null;
		
		for (HandlerMethodReturnValueHandler handler : handlers) {
			if (handler instanceof RequestResponseBodyMethodProcessor) {
				responseBodyProcessor = (RequestResponseBodyMethodProcessor) handler;
				break;
			}
		}
		
		return responseBodyProcessor;
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		ResourceHandlerRegistration resourceHandlerRegistration = registry.addResourceHandler("/resources/**");
		resourceHandlerRegistration.addResourceLocations("/resources/", "classpath:/META-INF/web-resources/");
	}
	
	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.addAll(handlerMethodArgumentResolvers);
	}
	
	@Override
	protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		returnValueHandlers.addAll(handlerMethodReturnValueHandlers);
	}
	
	@Bean
	public ViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setViewNames(new String[]{"jsp/*"});
		internalResourceViewResolver.setOrder(2);
		
		return internalResourceViewResolver;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("locale");
		
		return localeChangeInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	/**
	 * Create the CNVR. Specify the view resolvers to use explicitly. Get Spring to inject
	 * the ContentNegotiationManager created by the configurer (see previous method).
	 */
	/*
	 * @Bean
	 * public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
	 * // Define the view resolvers
	 * List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
	 * XmlViewResolver r1 = new XmlViewResolver();
	 * resolver.setLocation(new ServletContextResource(servletContext,
	 * "/WEB-INF/spring/spreadsheet-views.xml"));
	 * resolvers.add(r1);
	 * InternalResourceViewResolver r2 = new InternalResourceViewResolver();
	 * r2.setPrefix("WEB-INF/views");
	 * r2.setSuffix(".jsp");
	 * resolvers.add(r2);
	 * // Create the CNVR plugging in the resolvers and the content-negotiation manager
	 * ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
	 * resolver.setViewResolvers(resolvers);
	 * resolver.setContentNegotiationManager(manager);
	 * return resolver;
	 * }
	 */
	
	// seems to be required for @ControllerAdvice to work
	/*
	 * @Bean
	 * public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {
	 * return new ExceptionHandlerExceptionResolver();
	 * }
	 */
	
}
