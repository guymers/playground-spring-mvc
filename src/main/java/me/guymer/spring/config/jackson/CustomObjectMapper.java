package me.guymer.spring.config.jackson;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -4946606814162365981L;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public CustomObjectMapper() {
		super();

		setDateFormat(DATE_FORMAT);
	}
}
