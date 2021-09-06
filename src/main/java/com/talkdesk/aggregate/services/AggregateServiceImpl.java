package com.talkdesk.aggregate.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;
import com.talkdesk.aggregate.exception.InvalidParameterException;

import com.google.gson.Gson;
import com.talkdesk.aggregate.exception.ExternalServiceNotFoundException;
import com.talkdesk.aggregate.exception.NoPrefixesFilesFoundException;
import com.talkdesk.aggregate.model.datatransferobject.AggregateResponse;
import com.talkdesk.aggregate.model.datatransferobject.PrefixDTO;
import com.talkdesk.aggregate.model.datatransferobject.SectorSumDTO;
import com.talkdesk.aggregate.model.domainobject.Phone;
import com.talkdesk.aggregate.model.domainvalue.Sector;
import com.talkdesk.aggregate.repository.Connection;
import com.talkdesk.aggregate.repository.FileReader;


/**
* <h1>AggregateService</h1>
*  AggregateServiceImpl is the main service layer class that we will be using to
*  process the business logic and connect with the integration layer.
*  
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-15
*/
@Service
public class AggregateServiceImpl implements AggregateService{
	
	private final static String POSTURL = "https://challenge-business-sector-api.meza.talkdeskstg.com/sector/";
	private final static String PLUS = "+";
	private final static String DZERO = "00";
	private final static String SPACE = " ";	
	private List<String> prefixList;
	private static final String PHONE_STRING = "phoneString";
	
	/**
	 * The deafult constructor, also load the files of prefixes.
	 * @throws IOException if the files can't be read.
	 */			
	public AggregateServiceImpl() throws IOException {
		super();
		loadPrefixes();
	}
	
	/**
	 * This method load the files into the prefixList.
	 * @throws IOException if the files can't be read.
	 */			
	public void loadPrefixes() throws IOException {
		this.prefixList = this.getPrefixes();
	}	
	
	/**
	 * This method clean the loaded prefixList.
	 */		
	public void clearPrefixes() {
		prefixList.clear();
	}		
	
	/**
	 * <p>
	 * This method a load all the prefixes into a List.
	 * </p>
	 * @return List Return a List with all the loaded prefixes.
	 * @throws IOException if the files can't be read.
	 */			
	public List<String> getPrefixes() throws IOException{
    	FileReader fileReader = new FileReader();
    	List<String> prefixList = fileReader.loadPrefixes();
		return prefixList;
	}
	
	/**
	 * <p>
	 * This method receives a phone number and verify it if starts with a +.
	 * </p>
	 * @param number Receive a String phone number.
	 * @return boolean Return if the number starts with a +.
	 */		
	private boolean isPlus(String number) {
		boolean check = false;
		
		if(number.substring(0, 1).equals(PLUS)) {
			check = true;
		}
		return check;
	}
	
	/**
	 * <p>
	 * This method receives a phone number and verify it if starts with a 00.
	 * </p>
	 * @param number Receive a String phone number.
	 * @return boolean Return if the number starts with a 00.
	 */		
	private boolean isZeroZero(String number) {
		boolean check = false;
		
		if(number.substring(0, 2).equals(DZERO)) {
			check = true;
		}
		
		return check;
	}	
	
	/**
	 * <p>
	 * This method receives a String and verify it if has some alphabetic character.
	 * </p>
	 * @param s Receive a String .
	 * @return boolean Return if the number starts with a +.
	 */		
	private boolean hasAlpha(String s) {
		return s != null && s.chars().anyMatch(Character::isLetter);
	}	
	
	/**
	 * <p>
	 * This method receives a phone number and return if has valid prefix.
	 * </p>
	 * @param number Receive a String phone number.
	 * @return boolean Return if has valid prefix.
	 */			
	private boolean isPrefixValid(String number){
		boolean check = false;
		
		//Second character is Space
		if(number.substring(1,2).equals(SPACE)) {
			return check;
		}
		
		//3 digits or more than 6 and less than 13
		int size = number.replaceAll("\\D", "").length();
		if(!((size == 3) || (size>6 && size<13))) {
			return check;
		}		
		
		//Has some alphabetic character
		if(hasAlpha(number)) {
			return check;		
		}
		
		//If starts with + or 00
		if((isPlus(number)) || (isZeroZero(number))){
			check = true;
		}			
		
		return check;
	}	
	
	/**
	 * <p>
	 * This method receives a phone number and return if has valid prefix.
	 * </p>
	 * @param number Receive a String phone number.
	 * @return boolean Return if has valid prefix.
	 */				
	private String prefixExist(String number) {
		String prefixReturn = null;		
		String prefixConcat = "";
		
		//If start with + or 00 save this characters
		if(isPlus(number)) {
			prefixConcat = PLUS;
		} else if(isZeroZero(number)) {
			prefixConcat = DZERO;
		}
		
		//Search every prefix loaded
		for (String prefix : this.prefixList) {
			
			//Concatenate the current prefix from the file with + or 00
			String prefixTemp = prefixConcat.concat(prefix);
			int prefixSize = prefixTemp.length();
			
			//Removing the spaces before search
			String numberNoSpace = number.replaceAll("\\s", "");
			
			//Get the substring from the number at the same size from current prefix
			String prefixFromNumber = numberNoSpace.substring(0, prefixSize);
			
			//if found it, return
			if(prefixFromNumber.equals(prefixTemp)) {
				prefixReturn = prefix;
				break;
			}
			
		}
		
		return prefixReturn;
	}
	
	/**
	 * <p>
	 * This method load the Sector from the external service, based o the phone number parameter.
	 * </p>
	 * @param number Receive a String phone number.
	 * @return Phone Return a Phone Object with a Sector.
	 * @throws ClientProtocolException if the external service can't be linked.
	 * @throws IOException if the data can't be read.
	 */					
	private Phone loadSector(String number) throws ClientProtocolException, IOException {
		
		Connection conn = new Connection();
		Phone phone = conn.submit(POSTURL, number);
		return phone;
		
	}

	/**
	 * <p>
	 * This method add the prefix and sector into the Current result list.
	 * </p>
	 * @param prefixSumList Receive a List with the current prefixes, sector and total.
	 * @param sector Receive a Sector.
	 * @param prefix Receive a prefix.
	 * @return List Return a updated List of PrefixDTO.
	 */				
	private List<PrefixDTO> checkAndAdd(List<PrefixDTO> prefixSumList, Sector sector, String prefix) {
		boolean update = false;
		
		
		for (PrefixDTO prefixDTO : prefixSumList) {
			
			//Try to find if this prefix exist in the SumList.
			if(prefixDTO.getPrefix().equals(prefix)) {
				
				for (SectorSumDTO sectorSumDTO : prefixDTO.getListSectorSum()) {
					
					//If is the same business sector, then plus one to the sum
					if(sectorSumDTO.getSector().equals(sector)) {
						sectorSumDTO.plusOne();
						update = true;
					}
				}
				
			}
			
		}
		
		//If its not updated, then add a new one
		if(!update){
			List<SectorSumDTO> listSector = new ArrayList<SectorSumDTO>();
			SectorSumDTO ss1 = new SectorSumDTO(sector, 1);
			listSector.add(ss1);
			PrefixDTO prefixAdd = new PrefixDTO(prefix, listSector); 
			prefixSumList.add(prefixAdd);
		}
		
		return prefixSumList;
	}
	
	/**
	 * <p>
	 * This method process all the phone numbers received and return a service result Object.
	 * </p>
	 * @param phonesString Receive a List with Phone Numbers.
	 * @return AggregateResponse Return a Object with the service result.
	 * @throws NoPrefixesFilesFoundException 
	 * @throws ExternalServiceNotFoundException 
	 */



	public AggregateResponse processPhones(String phonesString) throws NoPrefixesFilesFoundException, ExternalServiceNotFoundException {
		
		List<PrefixDTO> prefixSumList = new ArrayList<PrefixDTO>();
		AggregateResponse response = new AggregateResponse();

		Gson gson = new Gson(); 
		String[] phonesList = gson.fromJson(phonesString, String[].class);  

		if(phonesList == null){
			throw new InvalidParameterException(PHONE_STRING, phonesList);
		}

		//go through all the phone numbers received
		for (String phoneNumber : phonesList) {
			
			//verify if its valid
			if(isPrefixValid(phoneNumber)){
				
				//if the prefix exist, save it
				String prefix = prefixExist(phoneNumber);
				
				if(prefix!=null) {
					//load the sector of this phone number and add to the current result list
					Phone phone = null;
					try {
						phone = loadSector(phoneNumber);
					} catch (ClientProtocolException e) {
						throw new ExternalServiceNotFoundException(e.getMessage());
					} catch (IOException e) {
						throw new NoPrefixesFilesFoundException(e.getMessage());						
					}
					prefixSumList = checkAndAdd(prefixSumList, phone.getSector(), prefix);
				}

			}
			
		}
		
		response.setPrefixSumList(prefixSumList);
		response.transformToJson();
		
		return response;		
	}	

}
