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
import com.sanbeso.dao.BrandDao;
import com.sanbeso.domain.Brand;
import com.sanbeso.exception.CustomValidationException;

@Repository("brandDao")
public class BrandDaoHibernate extends GeneralDao implements BrandDao {

	@Autowired
	@Qualifier("brandValidator")
	private Validator validator;
	
	private Object[] params = null;
	
	public BrandDaoHibernate(){
		params = new Object[1];
		params[0] = getMessageSource().getMessage("brand.title", null, Locale.getDefault()); 
	}
	
	private void validate(Brand brand, String movementKey) throws CustomValidationException, PersistenceException{
		Errors errors = new BeanPropertyBindingResult(brand, "brand");
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
	public Long add(Brand brand) throws CustomValidationException, PersistenceException{
		Long result = new Long(-1);
		validate(brand, DaoMessagesKeys.ADD_FAIL.getValue());
		try{
			result = (Long)getSession().save(brand);
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
	public Brand get(Brand brand) throws PersistenceException {
		Brand result = null;
		try{
			if(brand.getId()!=null && brand.getId()>0){
				result = getById(brand.getId());
			}else{
				List<Brand> results = prepareQuery(brand).list();
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
	public Brand getById(Long brandId) throws PersistenceException{
		Brand result = null;
		try{
			result = (Brand)getSession().get(Brand.class, brandId);
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
	public List<Brand> search(Brand brand) throws PersistenceException{
		List<Brand> results = new ArrayList<Brand>();
		try{
			results = prepareQuery(brand).list();	
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
	public void delete(Brand brand) throws PersistenceException{
		try{	
			getSession().delete(getSession().merge(brand));
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.DELETE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void update(Brand brand) throws CustomValidationException, PersistenceException {
		validate(brand, DaoMessagesKeys.UPDATE_FAIL.getValue());
		try{
			getSession().update(brand);
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.UPDATE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}
	
	public Criteria prepareQuery(Brand brand){
		Criteria criteria = getSession().createCriteria(Brand.class);
		if(brand !=null){
			if(brand.getName()!=null){
				criteria.add(Restrictions.eq("name", brand.getName()));
			}
			if(brand.getDescription()!=null){
				criteria.add(Restrictions.eq("description", brand.getDescription()));
			}
		}
		return criteria;
	}

}
