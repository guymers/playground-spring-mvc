package me.guymer.spring.config;

import java.util.HashSet;
import java.util.Set;

import me.guymer.spring.config.profile.Web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Web
@Configuration
public class ThymeleafConfig {

	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());

		Set<IDialect> additionalDialects = new HashSet<IDialect>();
		// additionalDialects.add(new org.thymeleaf.extras.tiles2.dialect.TilesDialect)
		// additionalDialects.add(new org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect)
		// additionalDialects.add(new org.thymeleaf.extras.conditionalcomments.dialect.ConditionalCommentsDialect)
		templateEngine.setAdditionalDialects(additionalDialects);

		return templateEngine;
	}

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setViewNames(new String[]{"templates/*"});
		resolver.setOrder(1);

		return resolver;
	}
}
