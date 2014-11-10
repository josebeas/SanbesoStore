package com.sanbeso.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author jose.beas
 *
 */
@Entity
@Table(name = "STATES")
public class State implements Serializable{

	private static final long serialVersionUID = -6495178542771729613L;
	
	@Id
	@Column(name = "\"STATEID\"")
    @GeneratedValue
    @JsonProperty("stateId")
	private Long id;
	@Column(name = "\"STATE\"")
	@JsonProperty("state")
	private String State;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	
	
}
