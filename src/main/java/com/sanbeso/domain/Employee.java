package com.sanbeso.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Jose Beas
 *
 */
@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9164443734197262883L;
	@Id
	@Column(name = "\"EMPLYEEID\"")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("employeeId")	
	private Long id;
	@Column(name = "\"FIRSNAME\"")
    @JsonProperty("firstName")	
	private String firstName;
	@Column(name = "\"LASTNAME\"")
    @JsonProperty("lastName")	
	private String lastName;
	@Column(name = "\"EMAIL\"")
    @JsonProperty("email")	
	private String email;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	
}
