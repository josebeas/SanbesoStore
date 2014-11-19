package com.sanbeso.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sanbeso.domain.Post;

/**
 * 
 * @author Jose Beas
 *
 */
@Component("employeeValidator")
public class EmployeeValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Post.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Post post = (Post)target;
		if(post!=null){
			ValidationUtils.rejectIfEmpty(errors, "fisrtName", "employee.validate.fisrtName");
			ValidationUtils.rejectIfEmpty(errors, "email", "employee.validate.email");
			ValidationUtils.rejectIfEmpty(errors, "lastName", "employee.validate.lastName");
		}
	}

}
