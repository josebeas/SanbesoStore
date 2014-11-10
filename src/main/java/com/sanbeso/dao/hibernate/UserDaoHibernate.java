package com.sanbeso.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import com.sanbeso.dao.DaoMessagesKeys;
import com.sanbeso.dao.GeneralDao;
import com.sanbeso.dao.UserDao;
import com.sanbeso.domain.User;
import com.sanbeso.exception.CustomValidationException;

/**
 * 
 * @author jose.beas
 *
 */
@Repository("userDao")
public class UserDaoHibernate extends GeneralDao implements UserDao {

	@Autowired
	@Qualifier("userValidator")
	private Validator validator;
	
	private Object[] params = null;
	
	public UserDaoHibernate(){
		params = new Object[1];
		params[0] = getMessageSource().getMessage("user.title", null, Locale.getDefault()); 
	}
	
	private void validate(User user, String movementKey) throws CustomValidationException{
		Errors errors = new BeanPropertyBindingResult(user, "user");
		if(errors.hasErrors()){
			String message = getMessageSource().getMessage(movementKey, params, Locale.getDefault());
			for(ObjectError error : errors.getAllErrors()){
				message += getMessageSource().getMessage(error.getDefaultMessage(), null, Locale.getDefault());
			}
			throw new CustomValidationException(message);
		}
	}
	
	@Override
	@Transactional
	public Long add(User user) throws CustomValidationException,
			PersistenceException {
		Long result = new Long(-1);
		try{
			result = (Long)getSession().save(user);
		}catch(Exception e){
			e.printStackTrace();
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.ADD_FAIL.getValue(), params, Locale.getDefault()) +
            		" " + e.getMessage(), e);
		}
		return result; 
	}

	@Override
	@Transactional
	public User get(User user) throws PersistenceException {
		User result = new User();
		try{
			if(user!=null && user.getId()!=null){
				result = getById(user.getId());
			}else{
				List<User> results = list(user);
				if(results.size()>0){
					throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.MULTIPLE_OBJECTS_FOUND.getValue(), params, Locale.getDefault()));
				}
				result = results.get(0);
			}
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.SEARCH_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional
	public User getById(Long userId) throws PersistenceException {
		try{
			User result = (User)getSession().get(User.class, userId);
			if(result == null)
				throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.OBJECT_NOT_FOUND.getValue(), params, Locale.getDefault()));
			else
				return result;
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.SEARCH_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> list(User user) throws PersistenceException {
		try{
			List<User> results = new ArrayList<User>();
			results = prepareQuery(user).list();
			if(results == null || results.size() == 0){
				throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.OBJECT_NOT_FOUND.getValue(), params, Locale.getDefault()));
			}
			return results;
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.SEARCH_FAIL.getValue(), params, Locale.getDefault())+" "+e.getMessage()); 
		}
	}

	@Override
	@Transactional
	public void update(User user) throws CustomValidationException,
			PersistenceException {
		validate(user, DaoMessagesKeys.UPDATE_FAIL.getValue());
		try{
			getSession().update(user);
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.UPDATE_FAIL.getValue(), params, Locale.getDefault())); 
		}
	}

	@Override
	@Transactional
	public void delete(User user) throws PersistenceException {
		try{
			Session session = getSession();
			User object = (User)session.get(User.class, user.getId());
			session.delete(session.merge(object));
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.DELETE_FAIL.getValue(), params, Locale.getDefault())+ " " + e.getMessage());
		}
	}
	
	public Criteria prepareQuery(User user){
		Criteria criteria = getSession().createCriteria(User.class);
		if(user !=null){
			if(user.getUsername()!=null)
				criteria.add(Restrictions.eq("username", user.getUsername()));
		}
		return criteria;
	}

}
