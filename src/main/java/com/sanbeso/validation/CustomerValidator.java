package com.sanbeso.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sanbeso.domain.Customer;

/**
 * 
 * @author jose.beas
 *
 */
@Component("customerValidator")
public class CustomerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Customer customer = (Customer)target;
		if(customer!=null){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "customer.validation.password");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "customer.validation.confirmPassword");
			if(customer.getFavFramework()!=null && customer.getFavFramework().length > 0){
				for(String favFramework : customer.getFavFramework()){
					if(favFramework == null || favFramework.equalsIgnoreCase("")){
						Object[] params = new Object[1];
						params[0]= customer.getPassword();
						errors.reject("favFramework", params, "customer.validation.favFramework");
					}
				}
			}
			if(!customer.getPassword().equalsIgnoreCase(customer.getConfirmPassword())){
				errors.rejectValue("password", "customer.validation.passwordMatch");
			}
		}
	}
}
