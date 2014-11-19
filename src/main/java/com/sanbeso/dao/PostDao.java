package com.sanbeso.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;

import com.sanbeso.domain.Post;
import com.sanbeso.exception.CustomValidationException;
/**
 * 
 * @author Jose Beas
 *
 */
@Repository("postDao")
public interface PostDao {
	
	public Long add(Post post) throws CustomValidationException, PersistenceException ;
	public Post get(Post post) throws PersistenceException;
	public Post getById(Long postId) throws PersistenceException;
	public List<Post> search(Post post) throws PersistenceException;
	public void delete(Post post) throws PersistenceException;
	public void update(Post post) throws CustomValidationException, PersistenceException;
	
}
