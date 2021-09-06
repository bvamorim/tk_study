package com.talkdesk.aggregate.model.datatransferobject;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
* <h1>AggregateResponse</h1>
*  A class to store the service result
*  The RawJson from this class will generate the final JSON.
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-15
*/
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AggregateResponse {
	
	private List<PrefixDTO> prefixSumList;
	private String rawJson;
	
	public void transformToJson() {

		JsonObject responseObj = new JsonObject();
					
		//Create a JSON Objet based on the result list with the Prefix, Sector and Total
		for (PrefixDTO prefixDTO : prefixSumList) {
			
			for (SectorSumDTO sectorSumDTO : prefixDTO.getListSectorSum()) {

				JsonObject tempObj = new JsonObject();
				
				tempObj.addProperty(sectorSumDTO.getSector().toString(), sectorSumDTO.getSum());				
				
				//If the element already exist, just get the other items
				if(responseObj.get(prefixDTO.getPrefix())!=null) {
					JsonElement responseE = responseObj.get(prefixDTO.getPrefix());
					JsonObject oldItems = responseE.getAsJsonObject();
					
			        for (Map.Entry<String, JsonElement> entry : oldItems.entrySet()) {
			        	tempObj.add(entry.getKey(), entry.getValue());
			        }
			        
				}
				
				responseObj.add(prefixDTO.getPrefix(), tempObj);					

			}			
		}
		
		rawJson = responseObj.toString();
		
	}

}
