package com.talkdesk.aggregate.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
* <h1>FileReader</h1>
*  A class to connect to the External Files,
*  get a List of Prefixes and verify if the received prefix exist.
*  
* @author  Bruno Amorim
* @version 1.0
* @since   2021-05-16
*/
public class FileReader {
	
	/**
	 * <p>
	 * This method load all the txt files at the classpath/files into a String List.
	 * </p>
	 * @return List Return a List with the files content.
	 * @throws IOException if the files can't be read.
	 */			
	public List<String> loadPrefixes() throws IOException {
		List<String> result = new ArrayList<>();

		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath*:files/*txt*");

		for (Resource resource: resources){
			if (resource.exists()) {
				List<String> tempList = readFileUsingStringBuilder(resource.getURL());
				for (String item : tempList) {
					result.add(item);
				}
			}
		}		

		return result;

	}
	
	/**
	 * <p>
	 * This method receives a Url and load the file content to a List.
	 * </p>
	 * @param URL Receive a URL where the file is located.
	 * @return List<String> Return a List with the file content.
	 * @throws IOException if the file can't be read.
	 */		
	private static List<String> readFileUsingStringBuilder(URL url) throws IOException
	{
		List<String> result = new ArrayList<>();
		String line;

		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

		while ((line = br.readLine()) != null) {
			result.add(line);
		}

		return result;
	}	

}
