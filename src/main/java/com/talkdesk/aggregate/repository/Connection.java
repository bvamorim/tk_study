package com.talkdesk.aggregate.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.talkdesk.aggregate.model.domainobject.Phone;

/**
* <h1>Connection</h1>
*  A class to connect to the External Service,
*  get the Sector and return a Phone object.
*  
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-16
*/
public class Connection {
	
	private final int STATUS_SUCESS = 200;
	private final String USER_AGENT = "Mozilla/5.0";
	private final String SPACE = " ";
	private final String SPACE_CODED = "%20";
	
	private HttpClient client = HttpClientBuilder.create().build();
	
	public Phone submit(String url, String number) throws ClientProtocolException, IOException {

		//Generate the URL access
		String getUrl = url.concat(number);
		
		//Replace the space to a space coded before submit
		getUrl = getUrl.replace(SPACE, SPACE_CODED);
		HttpGet request = new HttpGet(getUrl);
		
		// add request header
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);

		/*
		if(response.getStatusLine().getStatusCode()!=STATUS_SUCESS) {
			// throws exception TO-DO
			// Ignore the exception and continue
		}
		 */

		//Get the response
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	
		StringBuffer result = new StringBuffer();
		String line = "";
		
		//Transform into a StringBuffer
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		
        Gson gson = new GsonBuilder().create();
        
        //Load from the JSON StringBuffer to a Object
        Phone phone = gson.fromJson(result.toString(), Phone.class);		
	
		return phone;
	}		


}
