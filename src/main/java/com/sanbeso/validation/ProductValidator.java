package com.sanbeso.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sanbeso.domain.Product;
/**
 * 
 * @author jose.beas
 *
 */
@Component("productValidator")
public class ProductValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product user = (Product)target;
		if(user!=null){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "product.validation.password");
		}
	}

}
