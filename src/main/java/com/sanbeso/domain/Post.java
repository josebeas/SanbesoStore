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
@Table(name = "POSTS")
public class Post implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4618182501383709262L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "\"POSTID\"")
	@JsonProperty("postId")
	private Long id;
	@Column(name = "\"NAME\"")
	@JsonProperty("name")
	private String name;
	@Column(name = "\"CONTENT\"")
	@JsonProperty("content")
	private String content;
	@Column(name = "\"OWNER\"")
	@JsonProperty("owner")
	private User owner;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	
	
}
