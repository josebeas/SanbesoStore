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
@Component("postValidator")
public class PostValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Post.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Post post = (Post)target;
		if(post!=null){
			ValidationUtils.rejectIfEmpty(errors, "name", "post.validate.name");
			ValidationUtils.rejectIfEmpty(errors, "content", "post.validate.content");
			ValidationUtils.rejectIfEmpty(errors, "owner", "post.validate.owner");
		}
	}

}
