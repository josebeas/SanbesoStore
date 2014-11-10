package com.sanbeso.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;

import com.sanbeso.domain.User;
import com.sanbeso.exception.CustomValidationException;
/**
 * 
 * @author jose.beas
 *
 */
@Repository("userDao")
public interface UserDao {

	public Long add(User user) throws CustomValidationException, PersistenceException;
	public User get(User user)throws PersistenceException;
	public User getById(Long userId)throws PersistenceException;
	public List<User> list(User userId)throws PersistenceException;
	public void update(User user)throws CustomValidationException, PersistenceException;
	public void delete(User user)throws PersistenceException;
}
