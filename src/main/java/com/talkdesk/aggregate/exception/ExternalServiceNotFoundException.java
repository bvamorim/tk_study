package com.talkdesk.aggregate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
* <h1>ExternalServiceNotFoundException</h1>
* This Class is used to create a exception
* when the external web service is not online. 
* 
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-16
*/
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The external service cannot be reached.")
public class ExternalServiceNotFoundException extends Exception{

	private static final long serialVersionUID = -3754185217541985248L;
	
	
	public ExternalServiceNotFoundException(String message){
		super(message);
	}
}
