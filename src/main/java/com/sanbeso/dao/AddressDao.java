package com.sanbeso.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;

import com.sanbeso.domain.Address;
import com.sanbeso.exception.CustomValidationException;
/**
 * 
 * @author Jose Beas
 *
 */
@Repository("addressDao")
public interface AddressDao {
	
	public Long add(Address address) throws CustomValidationException, PersistenceException ;
	public Address get(Address address) throws PersistenceException;
	public Address getById(Long addressId) throws PersistenceException;
	public List<Address> search(Address address) throws PersistenceException;
	public void delete(Address address) throws PersistenceException;
	public void update(Address address) throws CustomValidationException, PersistenceException;
	
}
