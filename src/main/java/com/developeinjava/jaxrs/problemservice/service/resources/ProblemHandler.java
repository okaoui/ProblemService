package com.developeinjava.jaxrs.problemservice.service.resources;


import javax.ws.rs.core.Response;


public interface ProblemHandler {
	
	Response createProblem(int cid, String lang, String overview, String fullDescription);
	Response updateProblem(int problemId, String lang, String overview, String fullDescription);
	Response deleteProblem(int problemId);
	Response getProblems(String lang);
	Response getSingleXmlProblem(int pid);
	Response getSingleJsonProblem(int pid);
	Response login(String username, String password);
	Response getProblemsByAuthor(int aid);
	Response getTotalProblemsByAuthor(int aid);

}
