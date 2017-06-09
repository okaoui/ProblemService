package com.developeinjava.jaxrs.problemservice.errors;

/**
 * 
 * @author oussamakaoui
 *
 * Error class has an error code and error message, it loads the error message form 
 * error_messages.properties file
 */
public class Error{
	
	
	private int code;
	private String message;
	
	public Error(int code) {
		super();
		this.code = code;
	}

	public Error() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
