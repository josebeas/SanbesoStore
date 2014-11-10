package com.sanbeso.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "MODELS")
public class Model implements Serializable{

	private static final long serialVersionUID = 7896333240236181718L;
	
	@Id
	@Column(name = "\"MODELID\"")
    @GeneratedValue
    @JsonProperty("modelId")	
	private Long id;
	@Column(name = "\"NAME\"")
	@JsonProperty("name")	
	private String name;
	@Column(name = "\"DESCRIPTION\"")
	@JsonProperty("modelDescription")	
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
