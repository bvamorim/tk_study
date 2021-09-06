package com.talkdesk.aggregate.model.domainobject;

import com.talkdesk.aggregate.model.domainvalue.Sector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
* <h1>Phone</h1>
* This class represents a simple Phone.
*
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-15
*/
@Getter
@Setter
@AllArgsConstructor
public class Phone {
	
	private String number;
	private Sector sector;

}
