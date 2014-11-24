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
import com.sanbeso.dao.ModelDao;
import com.sanbeso.domain.Model;
import com.sanbeso.exception.CustomValidationException;

@Repository("modelDao")
public class ModelDaoHibernate extends GeneralDao implements ModelDao {

	@Autowired
	@Qualifier("modelValidator")
	private Validator validator;
	
	private Object[] params = null;
	
	public ModelDaoHibernate(){
		params = new Object[1];
		params[0] = getMessageSource().getMessage("model.title", null, Locale.getDefault()); 
	}
	
	private void validate(Model model, String movementKey) throws CustomValidationException, PersistenceException{
		Errors errors = new BeanPropertyBindingResult(model, "model");
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
	public Long add(Model model) throws CustomValidationException, PersistenceException{
		Long result = new Long(-1);
		validate(model, DaoMessagesKeys.ADD_FAIL.getValue());
		try{
			result = (Long)getSession().save(model);
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
	public Model get(Model model) throws PersistenceException {
		Model result = null;
		try{
			if(model.getId()!=null && model.getId()>0){
				result = getById(model.getId());
			}else{
				List<Model> results = prepareQuery(model).list();
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
	public Model getById(Long modelId) throws PersistenceException{
		Model result = null;
		try{
			result = (Model)getSession().get(Model.class, modelId);
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
	public List<Model> search(Model model) throws PersistenceException{
		List<Model> results = new ArrayList<Model>();
		try{
			results = prepareQuery(model).list();	
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
	public void delete(Model model) throws PersistenceException{
		try{	
			getSession().delete(getSession().merge(model));
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.DELETE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void update(Model model) throws CustomValidationException, PersistenceException {
		validate(model, DaoMessagesKeys.UPDATE_FAIL.getValue());
		try{
			getSession().update(model);
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.UPDATE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}
	
	public Criteria prepareQuery(Model model){
		Criteria criteria = getSession().createCriteria(Model.class);
		if(model !=null){
			if(model.getName()!=null){
				criteria.add(Restrictions.eq("name", model.getName()));
			}
			if(model.getDescription()!=null){
				criteria.add(Restrictions.eq("description", model.getDescription()));
			}
		}
		return criteria;
	}

}
