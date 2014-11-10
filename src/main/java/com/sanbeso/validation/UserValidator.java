package com.sanbeso.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sanbeso.domain.User;
/**
 * 
 * @author jose.beas
 *
 */
@Component("userValidator")
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;
		if(user!=null){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.validation.username");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.validation.password");
		}
	}

}
