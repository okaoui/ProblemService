package com.developeinjava.jaxrs.problemservice.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.developeinjava.jaxrs.problemservice.models.Author;
import com.developeinjava.jaxrs.problemservice.models.Problem;
import com.developeinjava.jaxrs.problemservice.service.resources.ProblemHandlerImpl;



public class ProblemDAOImpl implements ProblemDAO {
	private static Logger LOGGER = LoggerFactory.getLogger(ProblemHandlerImpl.class);
	
	private Session session; 
	public int insert(Problem problem) throws Exception{
		int id = -1;
		try{
			session = HibernateUtil.currentSession();
			Transaction  trans = session.beginTransaction();
			
			id = (Integer) session.save(problem);
			LOGGER.info("Problem created successfully :"+id);

			trans.commit();
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("failed!",e);
			return 0;
		}
		
		return id;
	}
	
	public int update(Problem problem) throws Exception{
		try{
			session = HibernateUtil.currentSession();
			Transaction  trans = session.beginTransaction();
			
			session.update(problem);
			LOGGER.info("Problem updated successfully");
			trans.commit();
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("failed!", e);
			return 0;
		}
		
		return 1;
	}

	public int delete(Problem problem) throws Exception{
		
		try{
			session = HibernateUtil.currentSession();
			Transaction  trans = session.beginTransaction();
			
			session.delete(problem);
			LOGGER.info("Problem deleted successfully");
			trans.commit();
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("failed!", e);
			return 0;
		}
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public List<Problem> get(Problem sample) throws Exception{
		List<Problem> plist = null;
		try{
			session = HibernateUtil.currentSession();
			Criteria criteria=session.createCriteria(Problem.class);
		
			if(sample.getProgramingLanguage() != "")
				criteria.add(Example.create(sample).ignoreCase());
		
			plist = criteria.list();
		}catch(Exception e){
			e.printStackTrace();
			return plist;
		}
		
		
		return plist;
	}
	
	

	public Problem get(int id) throws Exception{
		Problem p = null;
		try{
			session = HibernateUtil.currentSession();
			p = (Problem)session.get(Problem.class, id);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("failed!", e);
			return null;
		}
		return p;
	}

	public boolean findAuthor(Author author) throws Exception {
		Author a = null;
		try{
			session = HibernateUtil.currentSession();
			Criteria criteria=session.createCriteria(Author.class);
			
				Criterion username = Restrictions.eq("username", author.getUsername()).ignoreCase();
				Criterion password = Restrictions.eq("password", author.getPassword());
				criteria.add(Restrictions.and(username, password));
		
			a = (Author) criteria.uniqueResult();
			
			if(a == null){
				return false;
			}
		}catch(Exception e){
			LOGGER.error("findAuthor failed!", e);
			e.printStackTrace();
		}
		
		return true;
	}
	
	

	public long totalProblemsByAuthor(Author author) throws Exception {
		long  total = 0l;
		try{
			session = HibernateUtil.currentSession();
			Criteria criteria=session.createCriteria(Problem.class);
			criteria.add(Restrictions.eq("author", author));
			criteria.setProjection(Projections.rowCount());
			
			total = (Long) criteria.uniqueResult();
		}catch(Exception e){
			LOGGER.error("totalProblemsByAuthor failed!", e);
		}
		

		return total;
	}
	
	public static void main(String [] args) throws Exception{
		/*Problem newp = new Problem();
		newp.setDateOfCreation(new Date());
		newp.setDateOfModification(new Date());
		newp.setFullDescription("soem overview ");
		newp.setOverview("some description");
		newp.setProgramingLanguage("java");
		//newp.setId(1);
		Author a = new Author();
		a.setId(1);
		newp.setAuthor(a);
		
		//new ProblemDAOImpl().insert(newp);
		
		Problem sample = new Problem();
		sample.setProgramingLanguage("JAVA");
		List<Problem> list = new ProblemDAOImpl().get(sample);
		Problem p = new Problem();
		p.setPId(25);
		int res = new ProblemDAOImpl().delete(p);
		for(Problem p:list){
			System.out.println("PID: "+p.getId()+"\nOverview:"+p.getOverview());
		}*/
		Author a2 = new Author();
		//a2.setAid(2);
		Author a = new Author();
		a.setUsername("okaoui@");
		a.setPassword("123.@pol");
		//a.setAid(3);
		//Problem p = new Problem();
		//p.setAuthor(a);
		System.out.println(new ProblemDAOImpl().findAuthor(a));
		//System.out.println(new ProblemDAOImpl().totalProblemsByAuthor(a2));
		
	}

}
