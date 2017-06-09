package com.developeinjava.jaxrs.problemservice.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.developeinjava.jaxrs.problemservice.models.Author;
import com.developeinjava.jaxrs.problemservice.models.Problem;



public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	public static ThreadLocal<Session> thread = new ThreadLocal<Session>();
	
	private HibernateUtil(){
		try{
			Configuration configuration = new Configuration().configure();
			configuration.addAnnotatedClass(Author.class);
			configuration.addAnnotatedClass(Problem.class);
            ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
            registry.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = registry.buildServiceRegistry();
             
            sessionFactory = configuration.buildSessionFactory(serviceRegistry); 
            
		}catch(Throwable e){
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static Session currentSession() throws HibernateException{
		if(sessionFactory == null)
			new HibernateUtil();
		Session current = (Session) thread.get();
		if(current == null){
			current = sessionFactory.openSession();
			thread.set(current);
		}
		
		return current;
	}
	
	public static void closeSession() throws Exception{
		Session current = (Session)thread.get();
		if(current != null)
			current.close();
		thread.set(null);
	}

}
