package com.sanbeso.service;

import java.util.List;

import javax.persistence.PersistenceException;

import com.sanbeso.domain.Product;
import com.sanbeso.exception.CustomValidationException;

/**
 * 
 * @author jose.beas
 *
 */
public interface ProductService {

	public Long add(Product product) throws PersistenceException, CustomValidationException;
	public Product get(Product product);
	public Product getById(Long productId);
	public List<Product> list(Product product);
	public void update(Product product) throws PersistenceException, CustomValidationException ;
	public void delete(Product product);
}
