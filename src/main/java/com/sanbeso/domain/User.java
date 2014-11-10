package com.sanbeso.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author jose.beas
 *
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {

	private static final long serialVersionUID = -948111917594826743L;
	
	@Id
	@Column(name = "\"USERID\"")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("userId")	
	private Long id;
	@Column(name = "\"USERNAME\"")
	@JsonProperty("username")	
	private String username;
	@Column(name = "\"PASSWORD\"")
	@JsonProperty("password")	
	private String password;
	@Column(name = "\"MAIL\"")
	@JsonProperty("mail")	
	private String mail;
	@Column(name = "\"NAME\"")
	@JsonProperty("name")	
	private String name;
	@Column(name = "\"FIRSTNAME\"")
	@JsonProperty("firstname")	
	private String firstname;
	@Column(name = "\"LASTNAME\"")
	@JsonProperty("lastname")	
	private String lastname;
	@OneToMany(fetch = FetchType.LAZY, cascade ={CascadeType.ALL})
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonProperty("addresses")	
	private List<Address> addresses;
	@Column(name = "\"COUNTRY\"")
	@JsonProperty("country")	
	private String country;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddress(List<Address> addresses) {
		this.addresses = addresses;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
