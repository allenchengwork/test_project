package com.maplebox.validator;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import com.maplebox.table.Employee;

@Component
public class EmployeeValidator implements SmartValidator {
	private static Logger log = LoggerFactory.getLogger(EmployeeValidator.class);
	
	private Locale defaultLocale = Locale.TAIWAN;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		log.debug("validate 1*********");
		/*
		Employee employee = (Employee) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.employee.id");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.employee.name");
		*/
	}
	
	@Override
	public void validate(Object target, Errors errors, Object... validationHints) {
		log.debug("validate = {}", validationHints);
		
		ValidatorHelper validatorHelper = new ValidatorHelper(errors, messageSource, defaultLocale);
		Employee employee = (Employee) target;
		for (Object hint : validationHints) {
			if (hint instanceof EmployeeCreate) {
				
			} else if (hint instanceof EmployeeUpdate) {
				validatorHelper.checkNotEmpty(employee, "id");
			} else if (hint instanceof EmployeeDelete) {
				
			}
		}
		validatorHelper.checkNotEmpty(employee, "name");
		validatorHelper.checkLength(employee, "name", null, 10);
	}
	
	public static interface EmployeeCreate {
		
	}

	public static interface EmployeeUpdate {
		
	}
	
	public static interface EmployeeDelete {
		
	}
}
