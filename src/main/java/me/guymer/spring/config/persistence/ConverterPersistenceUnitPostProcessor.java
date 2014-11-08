package me.guymer.spring.config.persistence;

import javax.persistence.Converter;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ConverterPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {

	private final String basePackage = "com.github.marschall.threeten.jpa";

	// http://dev.eclipse.org/mhonarc/lists/eclipselink-users/msg08035.html
	@Override
	public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(Converter.class));

		for (BeanDefinition definition : provider.findCandidateComponents(basePackage)) {
			pui.addManagedClassName(definition.getBeanClassName());
		}
	}
}
