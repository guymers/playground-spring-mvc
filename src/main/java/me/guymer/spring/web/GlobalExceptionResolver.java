package me.guymer.spring.web;

import java.io.IOException;

import javax.inject.Inject;

import me.guymer.spring.config.jackson.CustomObjectMapper;
import me.guymer.spring.web.ext.ExtJson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ControllerAdvice
public class GlobalExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionResolver.class);

	@Inject
	private CustomObjectMapper objectMapper;

	@ExceptionHandler
	@ResponseBody
	public String handleException(Exception e) {
		LOGGER.warn("Handling exception", e);

		ExtJson extJson = new ExtJson();
		extJson.setSuccess(false);
		extJson.setMessage(e.getMessage());

		// returning an object gives a HttpMediaTypeNotAcceptableException, so convert to json ourselves
		return covertToJson(extJson);
	}

	private String covertToJson(ExtJson extJson) {
		String json = "";

		try {
			json = objectMapper.writeValueAsString(extJson);
		} catch (JsonGenerationException e) {
			json = fallbackJson();
		} catch (JsonMappingException e) {
			json = fallbackJson();
		} catch (IOException e) {
			json = fallbackJson();
		}

		return json;
	}

	private String fallbackJson() {
		return "{\"success\":false}";
	}
}
