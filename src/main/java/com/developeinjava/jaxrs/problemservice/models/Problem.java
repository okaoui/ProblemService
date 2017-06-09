package com.developeinjava.jaxrs.problemservice.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="problem")
@Entity
@Table(name="problem")
public class Problem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int pid;
	private Author author;
	private Date dateOfCreation;
	private Date dateOfModification;
	private String programingLanguage;
	private String overview;
	private String fullDescription;
	
	@XmlElement
	@Id @GeneratedValue @Column(name="pid") 
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	@XmlElement
	@ManyToOne @JoinColumn(name="aid", nullable=false)
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	@XmlElement
	@Column(name="date_of_creation", nullable=false)
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	@XmlElement
	@Column(name="date_of_modification", nullable=false)
	public Date getDateOfModification() {
		return dateOfModification;
	}
	public void setDateOfModification(Date dateOfModification) {
		this.dateOfModification = dateOfModification;
	}
	@XmlElement
	@Column(name="language", nullable=false, length=25)
	public String getProgramingLanguage() {
		return programingLanguage;
	}
	public void setProgramingLanguage(String programingLanguage) {
		this.programingLanguage = programingLanguage;
	}
	@XmlElement
	@Column(name="overview", nullable=false, length=2500)
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	@XmlElement
	@Column(name="fulldesc", nullable=false, length=10000)
	public String getFullDescription() {
		return fullDescription;
	}
	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}
	
	

}
