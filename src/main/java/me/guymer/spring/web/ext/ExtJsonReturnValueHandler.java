package me.guymer.spring.web.ext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

public class ExtJsonReturnValueHandler implements HandlerMethodReturnValueHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExtJsonReturnValueHandler.class);

	private final RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;

	public ExtJsonReturnValueHandler(RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor) {
		this.requestResponseBodyMethodProcessor = requestResponseBodyMethodProcessor;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.getMethodAnnotation(ExtJsonResponseBody.class) != null;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
		ExtJson extJson = getExtJsonFromReturnValue(returnValue, returnType);

		requestResponseBodyMethodProcessor.handleReturnValue(extJson, returnType, mavContainer, webRequest);
	}

	private ExtJson getExtJsonFromReturnValue(Object returnValue, MethodParameter returnType) {
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

		return extJson;
	}
}
