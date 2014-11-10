package com.sanbeso.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author jose.beas
 *
 */
@Entity
@Table(name = "ADRESSES")
public class Address {

	@Id
	@Column(name = "\"ADRESSID\"")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("userId")	
	private Long id;
	@Column(name = "\"STREET\"")
	@JsonProperty("street")
	private String street;
	@Column(name = "\"INTNUMBER\"")
	@JsonProperty("intNumber")
	private String intNumber;
	@Column(name = "\"EXTNUMBER\"")
	@JsonProperty("extNumber")
	private String extNumber;
	@Column(name = "\"COLONY\"")
	@JsonProperty("colony")
	private String Colony;
	@Column(name = "\"CITY\"")
	@JsonProperty("city")
	private String City;
	@OneToOne(fetch = FetchType.LAZY, targetEntity = State.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "\"STATE\"")
	@JsonProperty("state")
	private State state;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getIntNumber() {
		return intNumber;
	}
	public void setIntNumber(String intNumber) {
		this.intNumber = intNumber;
	}
	public String getExtNumber() {
		return extNumber;
	}
	public void setExtNumber(String extNumber) {
		this.extNumber = extNumber;
	}
	public String getColony() {
		return Colony;
	}
	public void setColony(String colony) {
		Colony = colony;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
}
