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
import com.sanbeso.dao.PostDao;
import com.sanbeso.domain.Post;
import com.sanbeso.exception.CustomValidationException;

@Repository("postDao")
public class PostDaoHibernate extends GeneralDao implements PostDao {

	@Autowired
	@Qualifier("postValidator")
	private Validator validator;
	
	private Object[] params = null;
	
	public PostDaoHibernate(){
		params = new Object[1];
		params[0] = getMessageSource().getMessage("post.title", null, Locale.getDefault()); 
	}
	
	private void validate(Post post, String movementKey) throws CustomValidationException, PersistenceException{
		Errors errors = new BeanPropertyBindingResult(post, "post");
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
	public Long add(Post post) throws CustomValidationException, PersistenceException{
		Long result = new Long(-1);
		validate(post, DaoMessagesKeys.ADD_FAIL.getValue());
		try{
			result = (Long)getSession().save(post);
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
	public Post get(Post post) throws PersistenceException {
		Post result = null;
		try{
			if(post.getId()!=null && post.getId()>0){
				result = getById(post.getId());
			}else{
				List<Post> results = prepareQuery(post).list();
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
	public Post getById(Long postId) throws PersistenceException{
		Post result = null;
		try{
			result = (Post)getSession().get(Post.class, postId);
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
	public List<Post> search(Post post) throws PersistenceException{
		List<Post> results = new ArrayList<Post>();
		try{
			results = prepareQuery(post).list();	
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
	public void delete(Post post) throws PersistenceException{
		try{	
			getSession().delete(getSession().merge(post));
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.DELETE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}

	@Override
	@Transactional
	public void update(Post post) throws CustomValidationException, PersistenceException {
		validate(post, DaoMessagesKeys.UPDATE_FAIL.getValue());
		try{
			getSession().update(post);
		}catch(Exception e){
			throw new PersistenceException(getMessageSource().getMessage(DaoMessagesKeys.UPDATE_FAIL.getValue(), params, Locale.getDefault()) + " " + e.getMessage());
		}
	}
	
	public Criteria prepareQuery(Post post){
		Criteria criteria = getSession().createCriteria(Post.class);
		if(post !=null){
			if(post.getName()!=null){
				criteria.add(Restrictions.eq("name", post.getName()));
			}
			if(post.getContent()!=null){
				criteria.add(Restrictions.eq("content", post.getContent()));
			}
			if(post.getOwner()!=null){
				criteria.add(Restrictions.eq("owner", post.getOwner()));
			}
		}
		return criteria;
	}

}
