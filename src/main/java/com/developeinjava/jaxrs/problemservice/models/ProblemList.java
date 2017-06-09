package com.developeinjava.jaxrs.problemservice.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name= "problems")
public class ProblemList {
	
	private List<Problem> list;
	
	public ProblemList(){
		list = new ArrayList<Problem>();
	}

	@XmlElement(name="problem")
	public List<Problem> getList() {
		return list;
	}

	public void setList(List<Problem> list) {
		this.list = list;
	}

}
