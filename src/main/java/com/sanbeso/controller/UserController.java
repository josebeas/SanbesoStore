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
import com.sanbeso.domain.User;
import com.sanbeso.service.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * 
 * @author jose.beas
 *
 */
@RestController
@RequestMapping (value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/users", description = "REST controller tah provides access to users table") 
public class UserController {

	private static final Logger LOG = Logger.getLogger(UserController.class);
	
	@Inject
	private UserService service; 
	
	@RequestMapping (value = "/test", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ApiOperation (value = "test users service", responseClass = "ValidateResponse", notes = "Shows a test users with no data")
	@ApiModel(type = User.class)
	public User test(){
		LOG.info("Request recived to test users service");
		return new User();
	}
	
	@RequestMapping (value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ApiOperation (value = "add a user", responseClass = "ValidateResponse", notes = "Add a user to database")
	@ApiModel(type = User.class)
	public Long add(@ApiParam(value = "User to add", required =  true )@RequestBody User user, BindingResult bindingResult) throws Exception{
		Long result = new Long(-1);
		if(!bindingResult.hasErrors()){
			try{
				result = (Long)service.add(user);
			}catch(PersistenceException e){
				throw new EntityNotFoundException(e.getMessage());
			}
		}else{
			throw new EntityNotFoundException("can´t bind user" + bindingResult); 
		}
		return result;
	}
	
	@RequestMapping (value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation (value = "gets an user", responseClass = "ValidateResponse", notes = "Gets a user from database")
	@ApiModel(type = User.class)
	public User get(@ApiParam(value = "User to get", required =  true )@RequestBody User user, BindingResult bindingResult) throws Exception{
		User result = new User();
		if(!bindingResult.hasErrors()){
			try{
				result = service.get(user);
			}catch(PersistenceException e){
				throw new EntityNotFoundException(e.getMessage());
			}
		}else{
			throw new EntityNotFoundException("can´t bind user" + bindingResult); 
		}
		return result;
	}
	
	@RequestMapping (value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation (value = "gets an user based on his id", responseClass = "ValidateResponse", notes = "Gets a user from database")
	@ApiModel(type = User.class)
	public User getById(@PathVariable("userId") Long userId) throws Exception{
		User result = new User();
		try{
			result = service.getById(userId);
		}catch(PersistenceException e){
			throw new EntityNotFoundException(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping (value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ApiOperation (value = "list of users", responseClass = "ValidateResponse", notes = "List all users from database")
	@ApiModel(type = User.class)
	public List<User> list(@ApiParam(value = "User to update", required =  true )@RequestBody User user, BindingResult bindingResult) throws Exception{
		List<User> results = new ArrayList<User>();
		if(!bindingResult.hasErrors()){
			try{
				results = service.list(user);
			}catch(PersistenceException e){
				throw new EntityNotFoundException(e.getMessage());
			}
			return results;
		}else{
			throw new EntityNotFoundException("can´t bind user" + bindingResult); 
		}
	}
	
	@RequestMapping (value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	@ApiOperation (value = "updates an user", responseClass = "ValidateResponse", notes = "Updates a user from database")
	@ApiModel(type = User.class)
	public void update(@ApiParam(value = "User to update", required =  true )@RequestBody User user, BindingResult bindingResult) throws Exception{
		if(!bindingResult.hasErrors()){
			try{
				service.update(user);
			}catch(PersistenceException e){
				throw new EntityNotFoundException(e.getMessage());
			}
		}else{
			throw new EntityNotFoundException("can´t bind user" + bindingResult); 
		}
	}
	
	@RequestMapping (value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	@ApiOperation (value = "deletes a user", responseClass = "ValidateResponse", notes = "Deletes a user from database")
	@ApiModel(type = User.class)
	public void delete(@ApiParam(value = "User to delete", required =  true )@RequestBody User user, BindingResult bindingResult) throws Exception{
		if(!bindingResult.hasErrors()){
			try{
				service.delete(user);
			}catch(PersistenceException e){
				throw new EntityNotFoundException(e.getMessage());
			}
		}else{
			throw new EntityNotFoundException("can´t bind user" + bindingResult); 
		}
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String handleException(Exception e) {
		return e.getMessage();
	}
	
	
}
