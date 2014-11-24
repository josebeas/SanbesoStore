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
import com.sanbeso.dao.CustomerDao;
import com.sanbeso.domain.Customer;
import com.sanbeso.exception.CustomValidationException;

@Repository("customerDao")
public class CustomerDaoHibernate extends GeneralDao implements CustomerDao {

	@Autowired
	@Qualifier("customerValidator")
	private Validator validator;
	
	private Object[] params = null;
	
	public CustomerDaoHibernate(){
		params = new Object[1];
		params[0] = getMessageSource().getMessage("customer.title", null, Locale.getDefault()); 
	}
	
	private void validate(Customer customer, String movementKey) throws CustomValidationException, PersistenceException{
		Errors errors = new BeanPropertyBindingResult(customer, "customer");
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
	public Long add(Customer customer) throws CustomValidationException, PersistenceException{
		Long result = new Long(-1);
		validate(customer, DaoMessagesKeys.ADD_FAIL.getValue());
		try{
			result = (Long)getSession().save(customer);
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
	public Customer get(Customer customer) throws PersistenceException {
		Customer result = null;
		try{
			if(customer.getId()!=null && customer.getId()>0){
				result = getById(customer.getId());
			}else{
				List<Customer> results = prepareQuery(customer).list();
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
	public Customer getById(Long customerId) throws PersistenceException{
		Customer result = null;
		try{
			result = (Customer)getSession().get(Customer.class, customerId);
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
	public List<Customer> search(Customer customer) throws PersistenceException{
		List<Customer> results = new ArrayList<Customer>();
		try{
			results = prepareQuery(customer).list();	
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
	public void delete(Customer customer) throws PersistenceException{
		try{	
			getSession().delete(getSession().merge(customer));
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.DELETE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void update(Customer customer) throws CustomValidationException, PersistenceException {
		validate(customer, DaoMessagesKeys.UPDATE_FAIL.getValue());
		try{
			getSession().update(customer);
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.UPDATE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}
	
	public Criteria prepareQuery(Customer customer){
		Criteria criteria = getSession().createCriteria(Customer.class);
		if(customer !=null){
			if(customer.getUserName()!=null){
				criteria.add(Restrictions.eq("userName", customer.getUserName()));
			}
			if(customer.getCountry()!=null){
				criteria.add(Restrictions.eq("country", customer.getCountry()));
			}
		}
		return criteria;
	}

}
