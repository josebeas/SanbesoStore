package com.sanbeso.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mangofactory.swagger.annotations.ApiModel;
import com.sanbeso.domain.Product;
import com.sanbeso.service.ProductService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 
 * @author jose.beas
 *
 */
@RestController
@RequestMapping (value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/products", description = "REST controller tah provides access to products table") 
public class ProductController {

	private static final Logger LOG = Logger.getLogger(ProductController.class);
	
	@Inject
	private ProductService service; 
	
	@RequestMapping (value = "/test", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation (value = "test product service", responseClass = "ValidateResponse", notes = "Shows a test products with no data")
	@ApiModel(type = Product.class)
	public Product test(){
		LOG.info("Request recived to test products service");
		return new Product();
	}
	
	@RequestMapping (value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ApiOperation (value = "add a product", responseClass = "ValidateResponse", notes = "Add a product to database")
	@ApiModel(type = Product.class)
	public Long add(@ApiParam(value = "Product to add", required =  true )@RequestBody Product product, BindingResult bindingResult) throws Exception{
		Long result = new Long(-1);
		if(!bindingResult.hasErrors()){
			try{
				result = (Long)service.add(product);
			}catch(PersistenceException e){
				throw new EntityNotFoundException(e.getMessage());
			}
		}else{
			throw new EntityNotFoundException("can´t bind product" + bindingResult); 
		}
		return result;
	}
	
	@RequestMapping (value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation (value = "gets an product", responseClass = "ValidateResponse", notes = "Gets a product from database")
	@ApiModel(type = Product.class)
	public Product get(@ApiParam(value = "Product to get", required =  true )@RequestBody Product product, BindingResult bindingResult) throws Exception{
		Product result = new Product();
		if(!bindingResult.hasErrors()){
			try{
				result = service.get(product);
			}catch(PersistenceException e){
				throw new EntityNotFoundException(e.getMessage());
			}
		}else{
			throw new EntityNotFoundException("can´t bind product" + bindingResult); 
		}
		return result;
	}
	
	@RequestMapping (value = "/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation (value = "gets an product based on his id", responseClass = "ValidateResponse", notes = "Gets a product from database")
	@ApiModel(type = Product.class)
	public Product getById(@PathVariable("productId") Long productId) throws Exception{
		Product result = new Product();
		try{
			result = service.getById(productId);
		}catch(PersistenceException e){
			throw new EntityNotFoundException(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping (value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ApiOperation (value = "list of products", responseClass = "ValidateResponse", notes = "List all products from database")
	@ApiModel(type = Product.class)
	public List<Product> list(@ApiParam(value = "Product to update", required =  true )@RequestBody Product product, BindingResult bindingResult) throws Exception{
		List<Product> results = new ArrayList<Product>();
		if(!bindingResult.hasErrors()){
			try{
				results = service.list(product);
			}catch(PersistenceException e){
				throw new EntityNotFoundException(e.getMessage());
			}
			return results;
		}else{
			throw new EntityNotFoundException("can´t bind product" + bindingResult); 
		}
	}
	
	@RequestMapping (value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	@ApiOperation (value = "updates an product", responseClass = "ValidateResponse", notes = "Updates a product from database")
	@ApiModel(type = Product.class)
	public void update(@ApiParam(value = "Product to update", required =  true )@RequestBody Product product, BindingResult bindingResult) throws Exception{
		if(!bindingResult.hasErrors()){
			try{
				service.update(product);
			}catch(PersistenceException e){
				throw new EntityNotFoundException(e.getMessage());
			}
		}else{
			throw new EntityNotFoundException("can´t bind product" + bindingResult); 
		}
	}
	
	@RequestMapping (value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	@ApiOperation (value = "deletes a product", responseClass = "ValidateResponse", notes = "Deletes a product from database")
	@ApiModel(type = Product.class)
	public void delete(@ApiParam(value = "Product to delete", required =  true )@RequestBody Product product, BindingResult bindingResult) throws Exception{
		if(!bindingResult.hasErrors()){
			try{
				service.delete(product);
			}catch(PersistenceException e){
				throw new EntityNotFoundException(e.getMessage());
			}
		}else{
			throw new EntityNotFoundException("can´t bind product" + bindingResult); 
		}
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String handleException(Exception e) {
		return e.getMessage();
	}
	
	
}
