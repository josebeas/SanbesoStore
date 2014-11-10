package com.sanbeso.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import com.sanbeso.config.AppConfig;


/**
 * 
 * @author jose.beas
 *
 */
@Repository
public class GeneralDao {
	
	@PersistenceContext()
	protected EntityManager em;
	
	protected MessageSource messages;
	
	public MessageSource getMessageSource(){
		if(messages == null){
			AppConfig appConfig = new AppConfig();
			messages = appConfig.messageSource();
		}
		return messages;
	}
	
	public Session getSession(){
		return em.unwrap(Session.class);
	}

	
}
