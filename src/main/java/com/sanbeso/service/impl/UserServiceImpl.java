package com.sanbeso.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sanbeso.dao.UserDao;
import com.sanbeso.domain.User;
import com.sanbeso.exception.CustomValidationException;
import com.sanbeso.service.UserService;

/**
 * 
 * @author jose.beas
 *
 */
@Service
public class UserServiceImpl implements UserService{

	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
	
	@Inject
	UserDao dao;
	
	@Override
	public Long add(User user) throws PersistenceException, CustomValidationException {
		LOG.info("Adding a user to database");
		return dao.add(user);
	}

	@Override
	public User get(User user) {
		LOG.info("Getting a user from database");
		User result = null;
		result = dao.get(user);
		return result;
	}

	@Override
	public User getById(Long userId) {
		LOG.info("Getting a user from database based on his database");
		User result = null;
		result = dao.getById(userId);
		return result;
	}

	@Override
	public void update(User user)throws PersistenceException, CustomValidationException {
		LOG.info("Updating a user from database");
		dao.update(user);
	}

	@Override
	public void delete(User user) {
		LOG.info("Deleting a user from database");
		dao.delete(user);
	}

	@Override
	public List<User> list(User user) {
		LOG.info("Listing all user from database based on a criteria");
		List<User> results = dao.list(user);
		return results;
	}

}
