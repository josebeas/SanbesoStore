package com.sanbeso.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;

import com.sanbeso.domain.Model;
import com.sanbeso.exception.CustomValidationException;
/**
 * 
 * @author jose.beas
 *
 */
@Repository("modelDao")
public interface ModelDao {

	public Long add(Model model) throws CustomValidationException, PersistenceException;
	public Model get(Model model)throws PersistenceException;
	public Model getById(Long modelId)throws PersistenceException;
	public List<Model> search(Model model)throws PersistenceException;
	public void update(Model model)throws CustomValidationException, PersistenceException;
	public void delete(Model model)throws PersistenceException;
}
