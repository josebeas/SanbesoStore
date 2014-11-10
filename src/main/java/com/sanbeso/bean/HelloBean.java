package com.sanbeso.bean;


/**
 * 
 * @author jose.beas
 *
 */
public class HelloBean {

	private String message;
	
	public HelloBean(String message){
		this.message = message;
	}

	/**
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "HelloBean{" +
				"message='" + message + '\'' +
				'}';
	}
	
}
