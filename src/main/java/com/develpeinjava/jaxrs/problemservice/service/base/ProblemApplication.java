package com.develpeinjava.jaxrs.problemservice.service.base;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.developeinjava.jaxrs.problemservice.service.resources.ProblemHandlerImpl;



@ApplicationPath("/problemStore")
public class ProblemApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.add(ProblemHandlerImpl.class);
		return set;
	}
	

}
