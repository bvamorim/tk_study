package com.talkdesk.aggregate.model.datatransferobject;

import com.talkdesk.aggregate.model.domainvalue.Sector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* <h1>SectorSumDTO</h1>
*  A class to store the total quantity by sector.
*  
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-15
*/
@Getter
@Setter
@AllArgsConstructor
@ToString
public class SectorSumDTO {
	
	private Sector sector;
	private Integer sum = 0;
	
	/**
	 * sum plus one
	 */
	public void plusOne() {
		this.sum = this.sum + 1;
	}	

}
