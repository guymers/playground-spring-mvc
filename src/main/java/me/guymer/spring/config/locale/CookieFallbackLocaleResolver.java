package me.guymer.spring.config.locale;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

public class CookieFallbackLocaleResolver extends CookieLocaleResolver {

	private static Logger LOGGER = LoggerFactory.getLogger(CookieFallbackLocaleResolver.class);

	private LocaleResolver fallbackLocaleResolver;

	public void setFallbackLocaleResolver(LocaleResolver fallbackLocaleResolver) {
		this.fallbackLocaleResolver = fallbackLocaleResolver;
	}

	@Override
	protected Locale determineDefaultLocale(HttpServletRequest request) {
		LOGGER.debug("Falling back to fallback resolver");

		return fallbackLocaleResolver.resolveLocale(request);
	}
}
