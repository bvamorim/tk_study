package com.talkdesk.aggregate.services;

import com.talkdesk.aggregate.exception.ExternalServiceNotFoundException;
import com.talkdesk.aggregate.exception.NoPrefixesFilesFoundException;
import com.talkdesk.aggregate.model.datatransferobject.AggregateResponse;

/**
* <h1>AggregateService</h1>
*  AggregateService is the service layer interface that will define
*  what the controller can use.
*  
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-15
*/
public interface AggregateService {

	AggregateResponse processPhones(String phonesString) throws NoPrefixesFilesFoundException, ExternalServiceNotFoundException;

}
