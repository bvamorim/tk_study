package com.talkdesk.aggregate.model.datatransferobject;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.talkdesk.aggregate.util.PrefizDTOSerializer;
import lombok.*;

/**
* <h1>PrefixDTO</h1>
*  A class to store the processed information.
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-15
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PrefixDTO {
	
	private String prefix;
	private List<SectorSumDTO> listSectorSum;

}
