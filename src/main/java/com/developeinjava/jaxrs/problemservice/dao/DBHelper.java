package com.developeinjava.jaxrs.problemservice.dao;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.developeinjava.jaxrs.problemservice.models.Author;
import com.developeinjava.jaxrs.problemservice.models.Problem;



@SuppressWarnings("deprecation")
public class DBHelper {

	public static Configuration getInitializedConfiguration(){
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(Author.class);
		config.addAnnotatedClass(Problem.class);
		config.configure();
		return config;
	}

	private static void recreateDatabase(){
		Configuration config = DBHelper.getInitializedConfiguration();
		new SchemaExport(config).create(true, true);
	}
	
	public static void main(String[] args){
		DBHelper.recreateDatabase();
	}
}
