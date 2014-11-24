package com.sanbeso.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;

import com.sanbeso.domain.Brand;
import com.sanbeso.exception.CustomValidationException;
/**
 * 
 * @author jose.beas
 *
 */
@Repository("brandDao")
public interface BrandDao {

	public Long add(Brand brand) throws CustomValidationException, PersistenceException;
	public Brand get(Brand brand)throws PersistenceException;
	public Brand getById(Long brandId)throws PersistenceException;
	public List<Brand> search(Brand brand)throws PersistenceException;
	public void update(Brand brand)throws CustomValidationException, PersistenceException;
	public void delete(Brand brand)throws PersistenceException;
}
