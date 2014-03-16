package me.guymer.spring.config;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import me.guymer.spring.config.locale.CookieFallbackLocaleResolver;
import me.guymer.spring.config.profile.Web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Web
@Configuration
public class InternationalizationConfig {

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		messageSource.setBasename("i18n/messages");

		return messageSource;
	}

	@Bean
	public Locale defaultLocale() {
		return new Locale("en", "US");
	}

	@Bean
	public SessionLocaleResolver sessionLocaleResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(defaultLocale());

		return sessionLocaleResolver;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieFallbackLocaleResolver localeResolver = new CookieFallbackLocaleResolver();
		localeResolver.setCookieName("locale");
		localeResolver.setDefaultLocale(defaultLocale());
		localeResolver.setFallbackLocaleResolver(sessionLocaleResolver());

		return localeResolver;
	}
}
