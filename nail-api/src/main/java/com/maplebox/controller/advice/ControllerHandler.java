package com.maplebox.controller.advice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.maplebox.domain.DataResult;
import com.maplebox.domain.ResultCode;
import com.maplebox.domain.ValidError;

@RestControllerAdvice
public class ControllerHandler {
	static Logger log = LoggerFactory.getLogger(ControllerHandler.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.OK)
	public DataResult<ValidError> handleValidException(HttpServletRequest request, MethodArgumentNotValidException e, Locale locale) {
		log.error("handleValidException");
		ValidError validError = new ValidError();
		
		List<String> globalMessage = new ArrayList<>();
		List<ObjectError> globalErrors = e.getBindingResult().getGlobalErrors();
		for (ObjectError error : globalErrors) {
			globalMessage.add(messageSource.getMessage(error, locale));
		}
		if (globalMessage.size() > 0) {
			validError.setGlobalError(globalMessage);
		}
		
		Map<String, Map<String, String>> fieldMap = new HashMap<>();
		Map<String, String> fieldErrorMap = null;
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		for (FieldError error : fieldErrors) {
			if (fieldMap.containsKey(error.getField())) {
				fieldErrorMap = (Map<String, String>) fieldMap.get(error.getField());
			} else {
				fieldErrorMap = new HashMap<>();
				fieldMap.put(error.getField(), fieldErrorMap);
			}
			String errorCode = error.getCode();
			errorCode = errorCode.substring(errorCode.lastIndexOf('.')+1);
			fieldErrorMap.put(errorCode, messageSource.getMessage(error, locale));
		}
		validError.setFieldError(fieldMap);
		return new DataResult<ValidError>(false, ResultCode.BASE_VALID_ERROR, validError);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
    public DataResult<String> handleError404(HttpServletRequest request, Exception e, Locale locale)   {
		log.error("handleError404", e);
		String notFoundPage = messageSource.getMessage("not_found_page", null, locale);
		return new DataResult<String>(false, ResultCode.NOT_FOUND_PAGE, notFoundPage);
	}
	
	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public DataResult<String> handleAllThrowable(HttpServletRequest request, Throwable e, Locale locale) {
		log.error("handleAllThrowable", e);
		return new DataResult<String>(false, ResultCode.SERVER_ERROR, e.getMessage());
	}
}
