package com.developeinjava.jaxrs.problemservice.utils;

import java.util.Date;

import com.developeinjava.jaxrs.problemservice.models.Language;

/**
 * 
 * @author oussamakaoui
 *
 */
public class HelperClass {
	
	public static boolean isIdValid(int aid){
		if(aid > 0){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static Date isDateValid(String date){
		return null;
	}
	
	public static boolean isLanguageValid(String lang){
		boolean valid = false;
		
		
		if(lang.equalsIgnoreCase(Language.CSHARP.getValue())){
			valid = true;
		}else if(lang.equalsIgnoreCase(Language.JAVA.getValue())){
			valid = true;
		}else if(lang.equalsIgnoreCase(Language.CPLUSPLUS.getValue())){
			valid = true;
		}else if(lang.equalsIgnoreCase(Language.PHP.getValue())){
			valid = true;
		}
		return valid;
	}
	
	public static boolean isTextValid(String text, String maxLengthPrope){
		return true;
	}

}
