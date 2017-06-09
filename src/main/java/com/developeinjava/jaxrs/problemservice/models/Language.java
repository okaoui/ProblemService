package com.developeinjava.jaxrs.problemservice.models;

public enum Language {
	JAVA("java"),
	CSHARP("C#"),
	PHP("php"),
	C("c"),
	CPLUSPLUS("C++");

	final String value;
	
	private Language(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}


