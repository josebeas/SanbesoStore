package com.sanbeso.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;

import com.sanbeso.domain.State;
import com.sanbeso.exception.CustomValidationException;
/**
 * 
 * @author jose.beas
 *
 */
@Repository("stateDao")
public interface StateDao {

	public Long add(State state) throws CustomValidationException, PersistenceException;
	public State get(State state)throws PersistenceException;
	public State getById(Long stateId)throws PersistenceException;
	public List<State> search(State state)throws PersistenceException;
	public void update(State state)throws CustomValidationException, PersistenceException;
	public void delete(State state)throws PersistenceException;
}
