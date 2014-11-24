package com.sanbeso.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
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
import com.sanbeso.dao.StateDao;
import com.sanbeso.domain.State;
import com.sanbeso.exception.CustomValidationException;

@Repository("stateDao")
public class StateDaoHibernate extends GeneralDao implements StateDao {

	@Autowired
	@Qualifier("stateValidator")
	private Validator validator;
	
	private Object[] params = null;
	
	public StateDaoHibernate(){
		params = new Object[1];
		params[0] = getMessageSource().getMessage("post.title", null, Locale.getDefault()); 
	}
	
	private void validate(State state, String movementKey) throws CustomValidationException, PersistenceException{
		Errors errors = new BeanPropertyBindingResult(state, "state");
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
	public Long add(State state) throws CustomValidationException, PersistenceException{
		Long result = new Long(-1);
		validate(state, DaoMessagesKeys.ADD_FAIL.getValue());
		try{
			result = (Long)getSession().save(state);
		}catch(Exception e){
			e.printStackTrace();
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.ADD_FAIL.getValue(), params, Locale.getDefault()) +
            		" " + e.getMessage(), e);
		}
		return result; 
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public State get(State state) throws PersistenceException {
		State result = null;
		try{
			if(state.getId()!=null && state.getId()>0){
				result = getById(state.getId());
			}else{
				List<State> results = prepareQuery(state).list();
				if(results.size()> 0){
					throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.MULTIPLE_OBJECTS_FOUND.getValue(), params, Locale.getDefault()));
				}else{
					result = results.get(0);
				}
			}
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.SEARCH_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional
	public State getById(Long stateId) throws PersistenceException{
		State result = null;
		try{
			result = (State)getSession().get(State.class, stateId);
			if(result == null){
				throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.OBJECT_NOT_FOUND.getValue(), params, Locale.getDefault()));
			}else{
				return result;
			}
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.SEARCH_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<State> search(State state) throws PersistenceException{
		List<State> results = new ArrayList<State>();
		try{
			results = prepareQuery(state).list();	
			if(results.size()< 0){
				throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.OBJECT_NOT_FOUND.getValue(), params, Locale.getDefault()));
			}
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.SEARCH_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
		return results;
	}

	@Override
	@Transactional
	public void delete(State state) throws PersistenceException{
		try{	
			getSession().delete(getSession().merge(state));
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.DELETE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void update(State post) throws CustomValidationException, PersistenceException {
		validate(post, DaoMessagesKeys.UPDATE_FAIL.getValue());
		try{
			getSession().update(post);
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.UPDATE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}
	
	public Criteria prepareQuery(State state){
		Criteria criteria = getSession().createCriteria(State.class);
		if(state !=null){
			if(state.getState()!=null){
				criteria.add(Restrictions.eq("state", state.getState()));
			}
		}
		return criteria;
	}

}
