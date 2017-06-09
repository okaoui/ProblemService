package com.developeinjava.jaxrs.problemservice.service.resources;

import java.util.Date;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.developeinjava.jaxrs.problemservice.dao.ProblemDAO;
import com.developeinjava.jaxrs.problemservice.dao.ProblemDAOImpl;
import com.developeinjava.jaxrs.problemservice.models.Author;
import com.developeinjava.jaxrs.problemservice.models.Problem;
import com.developeinjava.jaxrs.problemservice.models.ProblemList;
import com.developeinjava.jaxrs.problemservice.utils.HelperClass;
import com.developeinjava.jaxrs.problemservice.utils.MessageProvider;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/")
public class ProblemHandlerImpl implements ProblemHandler {
	private static Logger LOGGER = LoggerFactory.getLogger(ProblemHandlerImpl.class);
	 
	
	private ProblemDAO proDao;
	private static final String LANG_NOT_FOUND="input.problem.lang";
	private static final String MAX_CHAR_OVERVIEW="input.problem.max_char_overview";
	private static final String MAX_CHAR_FULLDESC="input.problem.max_char_fulldesc";
	private static final String LOGIN_SUCCESS_MESSAGE="login.success.message";
	private static final String AUTHOR_ID_NOT="problem.not.found";
	private static final String NOT_AUTHOR_WITH_ID="author.not.exist";
	
	
	@POST
	@Produces({MediaType.TEXT_PLAIN})
	@Path("/create")
	public Response createProblem(@FormParam("aid") int aid, 
			 @FormParam("lang") String lang, @FormParam("overview") String overview, @FormParam("fulldesc") String fullDescription) {
		
		//System.out.println(aid+"-"+dateOfCreation+"-"+lang+"-"+overview+"-"+fullDescription);
		
		boolean validAid = HelperClass.isIdValid(aid);
		boolean validLang = HelperClass.isLanguageValid(lang);
		boolean validOverview = HelperClass.isTextValid(overview, MAX_CHAR_OVERVIEW);
		boolean validDesc = HelperClass.isTextValid(fullDescription, MAX_CHAR_FULLDESC);
		
		int pid = 0;
		String message=null;
		if(validAid && validLang && validOverview && validDesc){
			
			Problem newProblem = new Problem();
			Author author = new Author();
			author.setAid(aid);
			newProblem.setAuthor(author);
			newProblem.setDateOfCreation(new Date());
			newProblem.setDateOfModification(new Date());
			newProblem.setProgramingLanguage(lang);
			newProblem.setOverview(overview);
			newProblem.setFullDescription(fullDescription);
			
			try{
				proDao = new ProblemDAOImpl();
				pid = proDao.insert(newProblem);
				
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error("failed!",e);
				
			}
			
		}
		
		if(pid==0){
			message="There some problems, problem could not be created.";
			return Response.status(Response.Status.BAD_REQUEST).
					entity(message).type(MediaType.TEXT_PLAIN).build();
		}else{ // 1
			message = "Problem has been created: "+pid;
		}
		
		return Response.ok(message, "text/plain").build();
	}

	
	@PUT
	@Produces({MediaType.TEXT_PLAIN})
	@Path("/update")
	public Response updateProblem(@FormParam("pid") int pid, @FormParam("lang") String lang, 
			@FormParam("overview") String overview, @FormParam("fulldesc") String fullDescription) {
		
		boolean validPid = HelperClass.isIdValid(pid);
		boolean validLang = HelperClass.isLanguageValid(lang);
		boolean validOverview = HelperClass.isTextValid(overview, MAX_CHAR_OVERVIEW);
		boolean validDesc = HelperClass.isTextValid(fullDescription, MAX_CHAR_FULLDESC);
		
		String message=null;
		int mark = 0;
		if(validPid && validLang && validOverview && validDesc){
			try{
				proDao = new ProblemDAOImpl();
				Problem p = proDao.get(pid);
				p.setDateOfModification(new Date());
				p.setOverview(overview);
				p.setFullDescription(fullDescription);
				p.setProgramingLanguage(lang);
			
				mark = proDao.update(p);
				
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error("failed!",e);
				message="There some problems, problem could not be updated.";
			}
		}
		
		if(mark == 0){

			message="There are some issues, problem could not be updated.";
			return Response.status(Response.Status.BAD_REQUEST).
				entity(message).type(MediaType.TEXT_PLAIN).build();
		}else {// 1
			message = "Problem "+pid+" has been updated.";
		
		}
			
		return Response.ok(message, "text/plain").build();
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/delete/{pid:\\d+}")
	public Response deleteProblem(@PathParam("pid") int pid) {
		String message = null;
		
		boolean validId = HelperClass.isIdValid(pid);
		int mark = 0;
		if(validId){
			Problem p = new Problem();
			p.setPid(pid);
			try{
				proDao = new ProblemDAOImpl();
				mark = proDao.delete(p);
				
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error("failed!",e);
			}
		}
		
		if(mark == 0){
			message= "Problem with id "+pid+" is not found or could not be deleted.";
			return Response.status(Response.Status.BAD_REQUEST).
					entity(message).type(MediaType.TEXT_PLAIN).build();
		}else{ // 1
			message = "Problem "+pid+" has been deleted.";
		}
		
		return Response.ok(message, "text/plain").build();
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/get/json/{lang: [a-zA-Z]{1,10}}")
	
	public Response getProblems(@PathParam("lang") String lang) {
		Problem sample = new Problem();
		String message = "";
		
		if(!lang.isEmpty() && HelperClass.isLanguageValid(lang)){
			sample.setProgramingLanguage(lang);
		}else{
			message += MessageProvider.getMessage(LANG_NOT_FOUND);
		}
		
		proDao = new ProblemDAOImpl();
		List<Problem> list = null;
		try{
			list  = proDao.get(sample);
		}catch(Exception e){
			LOGGER.error("getProblems failed!", e);
		}
		
		String json = null;
		
		if(list != null){
			//System.out.println(list.size());
			ProblemList plist = new ProblemList();
			plist.setList(list);
		
			json = toJson(plist);
		}
		
			
		if(!message.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).
					entity(toJson(message)).
					type(MediaType.APPLICATION_JSON).
					build();	
			
		return Response.ok(json, "application/json").build();
		
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/get/xml/{lang: [a-zA-Z]{1,10}}")
	
	public Response getXmlProblems(@PathParam("lang") String lang) {
		Problem sample = new Problem();
		String message = "";
		
		if(!lang.isEmpty() && HelperClass.isLanguageValid(lang)){
			sample.setProgramingLanguage(lang);
		}else{
			message += MessageProvider.getMessage(LANG_NOT_FOUND);
		}
		ProblemList plist = null;
		if(message.isEmpty()){
			proDao = new ProblemDAOImpl();
			List<Problem> list = null;
			
			try{
				list = proDao.get(sample);
			}catch(Exception e){
				LOGGER.error("getXmlProblems failed!", e);
			}
			
			if(list != null){
				 plist = new ProblemList();
				plist.setList(list);
			}
			
		}else{
					
			return Response.status(Response.Status.BAD_REQUEST).
					entity(toJson(message)).
					type(MediaType.APPLICATION_XML).
					build();	
		
		}
		
		return Response.ok(plist, "application/xml").build();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML})
	@Path("/get/xml/{pid:\\d+}")
	public Response getSingleXmlProblem(@PathParam("pid") int pid){
		boolean validId = HelperClass.isIdValid(pid);
		Problem p = null;
		String message=null;
		if(validId){
			try{
				proDao = new ProblemDAOImpl();
				p = proDao.get(pid);
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error("failed!", e);
				message="There some problems, problem could not be loaded.";
			}
		}
		
		if(p==null){
			message="Problem with ID "+pid+" was not found.";
			return Response.status(Response.Status.BAD_REQUEST).
					entity(message).type(MediaType.TEXT_PLAIN).build();
		}
		
		return Response.ok(p, "application/xml").build();
		
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/get/json/{pid:\\d+}")
	public Response getSingleJsonProblem(@PathParam("pid") int pid){
		boolean validId = HelperClass.isIdValid(pid);
		Problem p = null;
		String message=null;
		if(validId){
			try{
				proDao = new ProblemDAOImpl();
				p = proDao.get(pid);
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error("failed!", e);
				message="There some problems, problem could not be loaded.";
			}
		}
		
		if(p==null){
			message="Problem with ID "+pid+" was not found.";
			return Response.status(Response.Status.BAD_REQUEST).
					entity(toJson(message)).type(MediaType.APPLICATION_JSON).build();
		}
		
		return Response.ok(toJson(p), "application/json").build();
		
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/login")
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		String message="";
		
		if(username!=null && !username.isEmpty() && password!=null && !password.isEmpty()){
			Author a = new Author();
			a.setUsername(username);
			a.setPassword(password);
			
			proDao = new ProblemDAOImpl();
			
			try{
				boolean flag = proDao.findAuthor(a);
				
				if(flag){
					message = MessageProvider.getMessage(LOGIN_SUCCESS_MESSAGE);
					LOGGER.info("Login message:",message);
				}
			}catch(Exception e){
				LOGGER.error("login failed!", e);
			}
			
		}
		return Response.ok(message, "text/plain").build();
	}
	
	private String toJson(ProblemList plist){
		String json = "If you see this, there's a problem.";
		try{
			json = new ObjectMapper().writeValueAsString(plist);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return json;
	}
	
	private String toJson(Object obj){
		String json = "If you see this, there's a problem.";
		try{
			json = new ObjectMapper().writeValueAsString(obj);
		}
		catch(Exception e){
			
		}
		return json;
	}

	@GET
	@Produces({MediaType.APPLICATION_XML})
	@Path("/get/xml/author/{aid:\\d+}")
	public Response getProblemsByAuthor(@PathParam("aid") int aid) {
		Problem sample = new Problem();
		String message = "";
		
		if(HelperClass.isIdValid(aid)){
			Author a  = new Author();
			a.setAid(aid);
			sample.setAuthor(a);
		}else{
			message += MessageProvider.getMessage(LANG_NOT_FOUND);
		}
		ProblemList plist = null;
		if(message.isEmpty()){
			proDao = new ProblemDAOImpl();
			List<Problem> list = null;
			
			try{
				list = proDao.get(sample);
			}catch(Exception e){
				LOGGER.error("getProblemsByAuthor failed with author ID "+aid, e);
			}
			
			if(list != null){
				 plist = new ProblemList();
				plist.setList(list);
			}
			
		}else{
					
			return Response.status(Response.Status.BAD_REQUEST).
					entity(message).
					type(MediaType.APPLICATION_XML).
					build();	
		
		}
		
		return Response.ok(plist, "application/xml").build();
	}


	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/get/json/total/{aid:\\d+}")
	public Response getTotalProblemsByAuthor(@PathParam("aid") int aid) {
		long total=0l;
		String message=null;
		if(HelperClass.isIdValid(aid)){
			Author a = new Author();
			a.setAid(aid);
			try{
				proDao = new ProblemDAOImpl();
				total = proDao.totalProblemsByAuthor(a);	
			}catch(Exception e){
				LOGGER.error("getTotalProblemsByAuthor failed with "+aid, e);
			}
		}
		if(total == 0){
			message = MessageProvider.getMessage(NOT_AUTHOR_WITH_ID)+aid;
			
			return Response.status(Response.Status.BAD_REQUEST).
					entity(toJson(message)).
					type(MediaType.APPLICATION_JSON).
					build();
		}
		return Response.ok(toJson(total), "application/json").build();
	}
	
	
}
