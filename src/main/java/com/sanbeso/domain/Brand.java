package com.sanbeso.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "BRANDS")
public class Brand implements Serializable{
	
	private static final long serialVersionUID = -5436499107773468812L;
	
	@Id
	@Column(name = "\"BRANDID\"")
    @GeneratedValue
    @JsonProperty("brandId")	
	private Long id;
	@Column(name = "\"NAME\"", length = 128)
	@JsonProperty("name")	
	private String name;
	@Column(name = "\"DESCRIPTION\"")
	@JsonProperty("BrandDescription")	
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
