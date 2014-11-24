package com.sanbeso.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sanbeso.domain.Brand;
/**
 * 
 * @author jose.beas
 *
 */
@Component("brandValidator")
public class BrandValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Brand.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Brand brand = (Brand)target;
		if(brand!=null){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "brand.validation.name");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brand", "brand.validation.description");
		}
	}

}
