package com.sanbeso.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sanbeso.domain.Address;
/**
 * 
 * @author jose.beas
 *
 */
@Component("addressValidator")
public class AddressValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Address.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Address address = (Address)target;
		if(address!=null){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "address.validation.street");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "intNumber", "address.validation.intNumber");
		}
	}

}
