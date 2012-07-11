package me.guymer.spring.config.mybatis;

import java.lang.reflect.Method;
import java.util.Map;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

/**
 * postProcessBeanDefinitionRegistry is not called when configuring spring via java
 * 
 * see https://jira.springsource.org/browse/SPR-9464
 */
@Configuration
public class MapperScannerJavaConfigurer implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		try {
			MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
			mapperScannerConfigurer.setApplicationContext(applicationContext);
			mapperScannerConfigurer.setProcessPropertyPlaceHolders(false);
			
			AnnotationAttributes annotationAttributes = getAnnotationAttributes(beanFactory);
			
			mapperScannerConfigurer.setBasePackage(annotationAttributes.getString("basePackage"));
			mapperScannerConfigurer.afterPropertiesSet();
			
			BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
			
			mapperScannerConfigurer.postProcessBeanDefinitionRegistry(beanDefinitionRegistry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ImportAware.setImportMetadata is not called if class implements BeanDefinitionRegistryPostProcessor
	 * 
	 * @param beanFactory
	 * @return
	 */
	private AnnotationAttributes getAnnotationAttributes(ConfigurableListableBeanFactory beanFactory) throws Exception {
		AnnotationAttributes annotationAttributes = null;
		Object importRegistry = beanFactory.getBean("importRegistry");
		
		Method method = importRegistry.getClass().getMethod("getImportingClassFor", String.class);
		method.setAccessible(true);
		
		Object getImportingClass = method.invoke(importRegistry, this.getClass().getSuperclass().getName());
		
		if (getImportingClass instanceof String) {
			String importingClass = (String) getImportingClass;
			
			AnnotationMetadata metadata = new SimpleMetadataReaderFactory().getMetadataReader(importingClass).getAnnotationMetadata();
			
			Map<String, Object> annotationAttributeMap = metadata.getAnnotationAttributes(EnableMyBatisMapperScanner.class.getName(), true);
			
			annotationAttributes = AnnotationAttributes.fromMap(annotationAttributeMap);
		}
		
		return annotationAttributes;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {}
	
}
