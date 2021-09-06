package com.talkdesk.aggregate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
* <h1>NoPrefixesFilesFoundException</h1>
* This Class is used to create a exception
* when the files cannot be loaded. 
* 
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-16
*/
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The prefixes files cannot be loaded.")
public class NoPrefixesFilesFoundException extends Exception{

	private static final long serialVersionUID = -3387516993334229948L;
	
	public NoPrefixesFilesFoundException(String message){
		super(message);
	}
}
