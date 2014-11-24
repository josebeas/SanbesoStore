package com.sanbeso.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "CUSTOMERS")
public class Customer{
	
	@Id
	@Column(name = "\"CUSTOMERID\"")
    @GeneratedValue
    @JsonProperty("customerId")	
	private Long id;
	//textbox
	@Column(name = "\"USERNAME\"")
	@JsonProperty("userName")
	private String userName;
	
	//textarea
	@Column(name = "\"ADDRESS\"")
	@JsonProperty("address")
	private String address;
	
	//password related
	@Column(name = "\"PASSWORD\"")
	@JsonProperty("password")
	private String password;
	@Column(name = "\"CONFIRMPASSWORD\"")
	@JsonProperty("confirmPassword")
	private String confirmPassword;
	
	//checkbox
	@Column(name = "\"RECEIVENEWSLETTER\"")
	@JsonProperty("receiveNewlatter")
	private boolean receiveNewsletter;
	@Column(name = "\"FAVFRAMEWORK\"")
	@JsonProperty("favFramework")
	private String [] favFramework;
	
	//radio button
	@Column(name = "\"FAVNUMBER\"")
	@JsonProperty("favNumber")
	private String favNumber;
	@Column(name = "\"SEX\"")
	@JsonProperty("sex")
	private String sex;
	
	//dropdown box
	@Column(name = "\"COUNTRY\"")
	@JsonProperty("country")
	private String country;
	@Column(name = "\"JAVASKILLS\"")
	@JsonProperty("BrandDescription")
	private String javaSkills;
	
	//hidden value
	@Column(name = "\"SECRETVALUE\"")
	@JsonProperty("secretValue")
	private String secretValue;
	
	public Long getId() {
		return id;
	}
	public void setSecretValue(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public boolean isReceiveNewsletter() {
		return receiveNewsletter;
	}
	public void setReceiveNewsletter(boolean receiveNewsletter) {
		this.receiveNewsletter = receiveNewsletter;
	}
	public String[] getFavFramework() {
		return favFramework;
	}
	public void setFavFramework(String[] favFramework) {
		this.favFramework = favFramework;
	}
	public String getFavNumber() {
		return favNumber;
	}
	public void setFavNumber(String favNumber) {
		this.favNumber = favNumber;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getJavaSkills() {
		return javaSkills;
	}
	public void setJavaSkills(String javaSkills) {
		this.javaSkills = javaSkills;
	}
	public String getSecretValue() {
		return secretValue;
	}
	public void setSecretValue(String secretValue) {
		this.secretValue = secretValue;
	}
}
