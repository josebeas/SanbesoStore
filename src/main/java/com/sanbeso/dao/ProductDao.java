package com.sanbeso.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;

import com.sanbeso.domain.Product;
import com.sanbeso.exception.CustomValidationException;
/**
 * 
 * @author jose.beas
 *
 */
@Repository("productDao")
public interface ProductDao {

	public Long add(Product product) throws CustomValidationException, PersistenceException;
	public Product get(Product product)throws PersistenceException;
	public Product getById(Long productId)throws PersistenceException;
	public List<Product> list(Product product)throws PersistenceException;
	public void update(Product product)throws CustomValidationException, PersistenceException;
	public void delete(Product product)throws PersistenceException;
}
