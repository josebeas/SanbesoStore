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
import com.sanbeso.dao.ProductDao;
import com.sanbeso.domain.Product;
import com.sanbeso.exception.CustomValidationException;

/**
 * 
 * @author jose.beas
 *
 */
@Repository("productDao")
public class ProductDaoHibernate extends GeneralDao implements ProductDao {

	@Autowired
	@Qualifier("productValidator")
	private Validator validator;
	
	private Object[] params = null;
	
	public ProductDaoHibernate(){
		params = new Object[1];
		params[0] = getMessageSource().getMessage("product.title", null, Locale.getDefault()); 
	}
	
	private void validate(Product product, String movementKey) throws CustomValidationException{
		Errors errors = new BeanPropertyBindingResult(product, "product");
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
	public Long add(Product product) throws CustomValidationException,
			PersistenceException {
		Long result = new Long(-1);
		validate(product, DaoMessagesKeys.UPDATE_FAIL.getValue());
		try{
			result = (Long)getSession().save(product);
		}catch(Exception e){
			e.printStackTrace();
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.ADD_FAIL.getValue(), params, Locale.getDefault()) +
            		" " + e.getMessage(), e);
		}
		return result; 
	}

	@Override
	@Transactional
	public Product get(Product product) throws PersistenceException {
		Product result = new Product();
		try{
			if(product!=null && product.getId()!=null){
				result = getById(product.getId());
			}else{
				List<Product> results = list(product);
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
	public Product getById(Long productId) throws PersistenceException {
		try{
			Product result = (Product)getSession().get(Product.class, productId);
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
	public List<Product> list(Product product) throws PersistenceException {
		try{
			List<Product> results = new ArrayList<Product>();
			results = prepareQuery(product).list();
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
	public void update(Product product) throws CustomValidationException,
			PersistenceException {
		validate(product, DaoMessagesKeys.UPDATE_FAIL.getValue());
		try{
			getSession().update(product);
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.UPDATE_FAIL.getValue(), params, Locale.getDefault())); 
		}
	}

	@Override
	@Transactional
	public void delete(Product product) throws PersistenceException {
		try{
			Session session = getSession();
			Product object = (Product)session.get(Product.class, product.getId());
			session.delete(session.merge(object));
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.DELETE_FAIL.getValue(), params, Locale.getDefault())+ " " + e.getMessage());
		}
	}
	
	public Criteria prepareQuery(Product product){
		Criteria criteria = getSession().createCriteria(Product.class);
		if(product !=null){
			if(product.getAvailability()!=null){
				criteria.add(Restrictions.eq("availability", product.getAvailability()));
			}
			if(product.getDescripction()!=null){
				criteria.add(Restrictions.eq("description", product.getDescripction()));
			}
		}
		return criteria;
	}

}
