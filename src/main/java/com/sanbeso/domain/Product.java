package com.sanbeso.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author jose.beas
 *
 */
@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable{

	private static final long serialVersionUID = -3996504065841817221L;

	@Id
	@Column(name = "\"PRODUCTID\"")
    @GeneratedValue
    @JsonProperty("productId")	
	private Long id;
	@Column(name = "\"SERIAL\"")
	@JsonProperty("serial")	
	private String serial;
	@OneToOne(cascade = CascadeType.ALL, targetEntity = Brand.class, fetch = FetchType.LAZY)
	@JsonProperty("brand")
	private Brand brand;
	@OneToOne(cascade = CascadeType.ALL, targetEntity = Model.class, fetch = FetchType.LAZY)
	@JsonProperty("model")
	private Model model;
	@Column(name = "\"DESCRIPTION\"")
	@JsonProperty("description")	
	private String description;
	@Column(name = "\"AVAILABILITY\"")
	@JsonProperty("availability")	
	private Integer availability;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public String getDescripction() {
		return description;
	}
	public void setDescripction(String description) {
		this.description = description;
	}
	public Integer getAvailability() {
		return availability;
	}
	public void setAvailability(Integer availability) {
		this.availability = availability;
	}
	
	
	
}
