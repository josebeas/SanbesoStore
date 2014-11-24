package com.sanbeso.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sanbeso.domain.Model;
/**
 * 
 * @author Jose Beas
 *
 */
@Component("modelValidator")
public class ModelValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Model.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Model model = (Model)target;
		if(model!=null){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "model.validation.name");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brand", "model.validation.description");
		}
	}

}
