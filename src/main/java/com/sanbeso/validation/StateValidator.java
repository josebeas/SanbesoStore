package com.sanbeso.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sanbeso.domain.State;
/**
 * 
 * @author jose.beas
 *
 */
@Component("stateValidator")
public class StateValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return State.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		State state = (State)target;
		if(state!=null){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "state.validation.state");
		}
	}

}
