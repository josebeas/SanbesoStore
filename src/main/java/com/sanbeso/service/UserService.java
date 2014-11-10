package com.sanbeso.service;

import java.util.List;

import javax.persistence.PersistenceException;

import com.sanbeso.domain.User;
import com.sanbeso.exception.CustomValidationException;

/**
 * 
 * @author jose.beas
 *
 */
public interface UserService {

	public Long add(User user) throws PersistenceException, CustomValidationException;
	public User get(User user);
	public User getById(Long userId);
	public List<User> list(User user);
	public void update(User user) throws PersistenceException, CustomValidationException ;
	public void delete(User user);
}
