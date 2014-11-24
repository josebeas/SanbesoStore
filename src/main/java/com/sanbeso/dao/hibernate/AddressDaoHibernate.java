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
import com.sanbeso.dao.AddressDao;
import com.sanbeso.domain.Address;
import com.sanbeso.exception.CustomValidationException;

@Repository("addressDao")
public class AddressDaoHibernate extends GeneralDao implements AddressDao {

	@Autowired
	@Qualifier("addressValidator")
	private Validator validator;
	
	private Object[] params = null;
	
	public AddressDaoHibernate(){
		params = new Object[1];
		params[0] = getMessageSource().getMessage("post.title", null, Locale.getDefault()); 
	}
	
	private void validate(Address address, String movementKey) throws CustomValidationException, PersistenceException{
		Errors errors = new BeanPropertyBindingResult(address, "address");
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
	public Long add(Address address) throws CustomValidationException, PersistenceException{
		Long result = new Long(-1);
		validate(address, DaoMessagesKeys.ADD_FAIL.getValue());
		try{
			result = (Long)getSession().save(address);
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
	public Address get(Address address) throws PersistenceException {
		Address result = null;
		try{
			if(address.getId()!=null && address.getId()>0){
				result = getById(address.getId());
			}else{
				List<Address> results = prepareQuery(address).list();
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
	public Address getById(Long addressId) throws PersistenceException{
		Address result = null;
		try{
			result = (Address)getSession().get(Address.class, addressId);
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
	public List<Address> search(Address address) throws PersistenceException{
		List<Address> results = new ArrayList<Address>();
		try{
			results = prepareQuery(address).list();	
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
	public void delete(Address address) throws PersistenceException{
		try{	
			getSession().delete(getSession().merge(address));
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.DELETE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void update(Address post) throws CustomValidationException, PersistenceException {
		validate(post, DaoMessagesKeys.UPDATE_FAIL.getValue());
		try{
			getSession().update(post);
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.UPDATE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}
	
	public Criteria prepareQuery(Address address){
		Criteria criteria = getSession().createCriteria(Address.class);
		if(address !=null){
			if(address.getCity()!=null){
				criteria.add(Restrictions.eq("city", address.getCity()));
			}
			if(address.getColony()!=null){
				criteria.add(Restrictions.eq("colony", address.getColony()));
			}
			if(address.getIntNumber()!=null){
				criteria.add(Restrictions.eq("owner", address.getIntNumber()));
			}
			if(address.getState()!=null){
				criteria.add(Restrictions.eq("state", address.getState()));
			}
			if(address.getStreet()!=null){
				criteria.add(Restrictions.eq("street", address.getStreet()));
			}
		}
		return criteria;
	}

}
