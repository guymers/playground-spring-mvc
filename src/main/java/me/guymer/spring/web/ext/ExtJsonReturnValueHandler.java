package me.guymer.spring.web.ext;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

@Component
public class ExtJsonReturnValueHandler implements HandlerMethodReturnValueHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExtJsonReturnValueHandler.class);
	
	private HandlerMethodReturnValueHandler requestResponseBodyMethodProcessor;
	
	@Inject
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	
	@PostConstruct
	public void init() {
		HandlerMethodReturnValueHandlerComposite returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> handlers = returnValueHandlers.getHandlers();
		
		for (HandlerMethodReturnValueHandler handler : handlers) {
			if (handler.getClass().equals(RequestResponseBodyMethodProcessor.class)) {
				requestResponseBodyMethodProcessor = handler;
			}
		}
	}
	
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.getMethodAnnotation(ExtJsonResponseBody.class) != null;
	}
	
	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
		ExtJson extJson;
		
		if (returnValue instanceof ExtJson) {
			extJson = (ExtJson) returnValue;
			
			if (extJson.getSuccess() == null) {
				LOGGER.debug("Setting success to true on ExtJson object");
				
				extJson.setSuccess(true);
			}
		} else {
			LOGGER.debug("Putting {} into ExtJson object", returnType.getMethod().getGenericReturnType());
			
			extJson = new ExtJson();
			extJson.setSuccess(true);
			extJson.setData(returnValue);
		}
		
		requestResponseBodyMethodProcessor.handleReturnValue(extJson, returnType, mavContainer, webRequest);
	}
	
}
