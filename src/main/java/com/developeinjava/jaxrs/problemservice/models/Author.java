package com.developeinjava.jaxrs.problemservice.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;


@XmlRootElement(name="author")
@Entity
@Table(name="author")
public class Author {
	
	private int aid;
	private String firstName;
	private String lastName;
	private Date birthday;
	private String email;
	private String role;
	private String mobile;
	private String username;
	private String password;
	private List<Problem> problems;
	
	@XmlElement
	@Id @GeneratedValue @Column(name="aid")
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	@XmlElement
	@Column(name="firstname", nullable=false, length=30)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@XmlElement
	@Column(name="lastname", nullable=false, length=30)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@XmlElement
	@Column(name="email", nullable=false, length=250) @Email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@XmlElement
	@Column(name="role", nullable=false, length=30)
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@XmlElement
	@Column(name="mobile", nullable=false, length=15)
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@XmlTransient @JsonIgnore
	@OneToMany(mappedBy="author", targetEntity=Problem.class,
			fetch=FetchType.EAGER, cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	public List<Problem> getProblems() {
		return problems;
	}
	public void setProblems(List<Problem> problems) {
		this.problems = problems;
	}
	
	@XmlTransient @JsonIgnore
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@XmlTransient @JsonIgnore
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
