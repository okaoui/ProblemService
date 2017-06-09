package com.developeinjava.jaxrs.problemservice.dao;

import java.util.List;

import com.developeinjava.jaxrs.problemservice.models.Author;
import com.developeinjava.jaxrs.problemservice.models.Problem;


public interface ProblemDAO {
	
	int insert(Problem problem) throws Exception;
	int update(Problem problem) throws Exception;
	int delete(Problem problem) throws Exception;
	List<Problem> get(Problem sample) throws Exception;
	Problem get(int id) throws Exception;
	boolean findAuthor(Author author) throws Exception;
	long totalProblemsByAuthor(Author author) throws Exception;
}
