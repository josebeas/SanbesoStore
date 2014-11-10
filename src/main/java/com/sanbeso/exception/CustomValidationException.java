package com.sanbeso.exception;

/**
 * 
 * @author jose.beas
 *
 */
public class CustomValidationException extends Exception{

	private static final long serialVersionUID = 5969643723060212165L;
	
	public CustomValidationException(){
		super();
	}

	public CustomValidationException(String message){
		super(message);
	}
	
	public CustomValidationException(String message, Throwable throwable){
		super(message, throwable);
	}
	
	public CustomValidationException(Throwable throwable){
		super(throwable);
	}
}
