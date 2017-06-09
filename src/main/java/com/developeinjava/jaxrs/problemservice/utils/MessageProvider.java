package com.developeinjava.jaxrs.problemservice.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MessageProvider {
	
	private static Properties prop = new Properties();
	private static InputStream input = null;

	
	static{
		
		try{
			String filename = "messages.properties";
			input = MessageProvider.class.getClassLoader().getResourceAsStream(filename);
			
			if(input==null){
				throw new FileNotFoundException();
			}
			
			prop.load(input);
			//System.out.println(prop.getProperty("input.problem.lang"));
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public static String getMessage(String key){
		if(key!=null&&!key.isEmpty())
			return prop.getProperty(key);
		else
			return "";
	}
	
	public static void main(String[] args){
		new MessageProvider();
	}

}
