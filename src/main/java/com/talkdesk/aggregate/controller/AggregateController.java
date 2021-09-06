package com.talkdesk.aggregate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talkdesk.aggregate.model.datatransferobject.PrefixDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.talkdesk.aggregate.exception.ExternalServiceNotFoundException;
import com.talkdesk.aggregate.exception.NoPrefixesFilesFoundException;
import com.talkdesk.aggregate.model.datatransferobject.AggregateResponse;
import com.talkdesk.aggregate.services.AggregateService;



/**
* <h1>AggregateController</h1>
* This Class integrate the business logic service 
* and the user requests.
* 
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-16
*/
@RestController
@Api(tags = { "Phone number information API" })
public class AggregateController {
	
	/**
	 * The aggregateService hold business logic
	 * and call method in integration layer.
	 */	 
	@Autowired
	private AggregateService aggregateService;

	
	/**
	 * <p>
	 * This method receives a Json with an Array of Strings(Phones)
	 * and returns a Json with the prefix, sector and the quantity.
	 * </p>
	 * @param phonesList This is the unique parameter Json with an Array of Phones.
	 * @return ResponseEntity<String> This returns a Json inside a String with the prefix, sector and the quantity.
	 * @throws ExternalServiceNotFoundException 
	 * @throws NoPrefixesFilesFoundException 
	 */
	@ApiOperation(value = "Return sectors from phone numbers")
	@RequestMapping(
		    value = "/aggregate", 
		    method = RequestMethod.POST,
		    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
		    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)	
	public @ResponseBody ResponseEntity<String> processPhones(@RequestBody String phonesList) throws NoPrefixesFilesFoundException, ExternalServiceNotFoundException {

		//Process the Numbers List e get a Result Object.
		AggregateResponse result = aggregateService.processPhones(phonesList);
		
        return new ResponseEntity<String>(result.getRawJson(), HttpStatus.OK);  
	  
	}
	
	/**
	 * <p>
	 * This method return a message if the service is online.
	 * </p>
	 * @return String This returns deafult message.
	 */
	@GetMapping("/verify")
	public String getVersion() {
		String result = "Talkdesk Test RESTService Successfully started..";

		return result;
	}

	@GetMapping("/teste")
	public String getTest() throws JsonProcessingException {

		PrefixDTO prefixDTO = new PrefixDTO("1", null);

		String serialized = new ObjectMapper().writeValueAsString(prefixDTO);


		return serialized;
	}

}
