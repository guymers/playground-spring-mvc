package me.guymer.spring.config.jackson;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import me.guymer.spring.config.profile.Web;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * http://magicmonster.com/kb/prg/java/spring/webmvc/jackson_custom.html
 */
@Web
@Component
public class CustomMappingJackonObjectMapper {

	@Inject
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

	@Inject
	private CustomObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter.getMessageConverters();

		for (HttpMessageConverter<?> messageConverter : messageConverters) {
			if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter converter = (MappingJackson2HttpMessageConverter) messageConverter;

				converter.setObjectMapper(objectMapper);
			}
		}
	}
}
