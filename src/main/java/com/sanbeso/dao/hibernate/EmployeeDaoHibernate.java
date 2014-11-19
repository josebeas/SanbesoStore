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
import com.sanbeso.dao.EmployeeDao;
import com.sanbeso.domain.Employee;
import com.sanbeso.exception.CustomValidationException;

/**
 * 
 * @author Jose Beas
 *
 */
@Repository("employeeDao")
public class EmployeeDaoHibernate extends GeneralDao implements EmployeeDao {

	@Autowired
	@Qualifier("employeeValidator")
	private Validator validator;
	
	private Object[] params = null;
	
	public EmployeeDaoHibernate(){
		params = new Object[1];
		params[0] = getMessageSource().getMessage("post.title", null, Locale.getDefault()); 
	}
	
	private void validate(Employee employee, String movementKey) throws CustomValidationException, PersistenceException{
		Errors errors = new BeanPropertyBindingResult(employee, "employee");
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
	public Long add(Employee employee) throws CustomValidationException, PersistenceException{
		Long result = new Long(-1);
		validate(employee, DaoMessagesKeys.ADD_FAIL.getValue());
		try{
			result = (Long)getSession().save(employee);
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
	public Employee get(Employee employee) throws PersistenceException {
		Employee result = null;
		try{
			if(employee.getId()!=null && employee.getId()>0){
				result = getById(employee.getId());
			}else{
				List<Employee> results = prepareQuery(employee).list();
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
	public Employee getById(Long employeeId) throws PersistenceException{
		Employee result = null;
		try{
			result = (Employee)getSession().get(Employee.class, employeeId);
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
	public List<Employee> search(Employee employee) throws PersistenceException{
		List<Employee> results = new ArrayList<Employee>();
		try{
			results = prepareQuery(employee).list();	
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
	public void delete(Employee employee) throws PersistenceException{
		try{	
			getSession().delete(getSession().merge(employee));
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.DELETE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void update(Employee employee) throws CustomValidationException, PersistenceException {
		validate(employee, DaoMessagesKeys.UPDATE_FAIL.getValue());
		try{
			getSession().update(employee);
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.UPDATE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}
	
	public Criteria prepareQuery(Employee employee){
		Criteria criteria = getSession().createCriteria(Employee.class);
		if(employee !=null){
			if(employee.getLastName()!=null){
				criteria.add(Restrictions.eq("firstName", employee.getLastName()));
			}
			if(employee.getEmail()!=null){
				criteria.add(Restrictions.eq("email", employee.getEmail()));
			}
			if(employee.getLastName()!=null){
				criteria.add(Restrictions.eq("lastName", employee.getLastName()));
			}
		}
		return criteria;
	}

}
