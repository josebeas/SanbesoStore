package com.sanbeso.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sanbeso.dao.ProductDao;
import com.sanbeso.domain.Product;
import com.sanbeso.exception.CustomValidationException;
import com.sanbeso.service.ProductService;

/**
 * 
 * @author jose.beas
 *
 */
@Service
public class ProductServiceImpl implements ProductService{

	private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class);
	
	@Inject
	ProductDao dao;
	
	@Override
	public Long add(Product product) throws PersistenceException, CustomValidationException {
		LOG.info("Adding a product to database");
		return dao.add(product);
	}

	@Override
	public Product get(Product product) {
		LOG.info("Getting a product from database");
		Product result = null;
		result = dao.get(product);
		return result;
	}

	@Override
	public Product getById(Long productId) {
		LOG.info("Getting a product from database based on his database");
		Product result = null;
		result = dao.getById(productId);
		return result;
	}

	@Override
	public void update(Product product)throws PersistenceException, CustomValidationException {
		LOG.info("Updating a product from database");
		dao.update(product);
	}

	@Override
	public void delete(Product product) {
		LOG.info("Deleting a product from database");
		dao.delete(product);
	}

	@Override
	public List<Product> list(Product product) {
		LOG.info("Listing all products from database based on a criteria");
		List<Product> results = dao.list(product);
		return results;
	}

}
