package com.sanbeso.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;

import com.sanbeso.domain.Customer;
import com.sanbeso.exception.CustomValidationException;
/**
 * 
 * @author jose.beas
 *
 */
@Repository("customerDao")
public interface CustomerDao {

	public Long add(Customer customer) throws CustomValidationException, PersistenceException;
	public Customer get(Customer customer)throws PersistenceException;
	public Customer getById(Long customerId)throws PersistenceException;
	public List<Customer> search(Customer customer)throws PersistenceException;
	public void update(Customer customer)throws CustomValidationException, PersistenceException;
	public void delete(Customer customer)throws PersistenceException;
}
