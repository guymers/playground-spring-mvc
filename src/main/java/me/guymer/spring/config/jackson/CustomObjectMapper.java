package me.guymer.spring.config.jackson;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@Component
public class CustomObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -4946606814162365981L;

	private static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(ISO8601);

	public CustomObjectMapper() {
		super();

		setDateFormat(DATE_FORMAT);
		registerModule(new JSR310Module());
	}
}
