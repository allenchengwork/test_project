package com.maplebox.nail.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Objects;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class ValidatorHelper {
	static Logger log = LoggerFactory.getLogger(ValidatorHelper.class);
	
	public static final String V_NOT_EMPTY = "NotEmpty";
	public static final String V_LEGNTH = "Length";
	public static final String V_NOT_BLANK = "NotBlank";
	
	private MessageSource messageSource;
	
	private Locale locale = Locale.TAIWAN;
	
	private Errors errors;
	
	public ValidatorHelper(Errors errors, MessageSource messageSource, Locale locale) {
		this.errors = errors;
		this.messageSource = messageSource;
		this.locale = locale;
	}
	
	public void checkNotEmpty(Object cls, String field) {
		String errorCode = getErrorCode(cls, field, V_NOT_EMPTY);
		String[] errorArgs = new String[] {field};
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, 
				errorCode, new String[] {field}, 
				messageSource.getMessage(errorCode, errorArgs, locale));
	}
	
	public void checkLength(Object cls, String field, Integer min, Integer max) {
		String errorCode = getErrorCode(cls, field, V_LEGNTH);
		String[] errorArgs = new String[] {field, Objects.toString(min, ""), Objects.toString(max, "")};
		String value = null;
		try {
			value = BeanUtils.getProperty(cls, field);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		
		if (value != null) {
			if ((min != null && value.length() < min)
				|| (max != null && value.length() > max)) {
				errors.rejectValue(field, errorCode, errorArgs, messageSource.getMessage(errorCode, errorArgs, locale));
			}
		}
	}
	
	public void checkNotBlank(Object cls, String field, Integer min, Integer max) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String errorCode = getErrorCode(cls, field, V_NOT_BLANK);
		String[] errorArgs = new String[] {field};
		String value = null;
		try {
			value = BeanUtils.getProperty(cls, field);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		
		if (value != null) {
			if (StringUtils.isNotBlank(value)) {
				return;
			}
		}
		errors.rejectValue(field, errorCode, errorArgs, messageSource.getMessage(errorCode, errorArgs, locale));
	}
	
	private String getErrorCode(Object cls, String field, String type) {
		return cls.getClass().getSimpleName()+"."+field+"."+type;
	}
}
