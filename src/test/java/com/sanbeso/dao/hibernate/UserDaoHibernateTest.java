package com.sanbeso.dao.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.sanbeso.dao.UserDao;
import com.sanbeso.domain.Address;
import com.sanbeso.domain.State;
import com.sanbeso.domain.User;
import com.sanbeso.exception.CustomValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/testContext.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class UserDaoHibernateTest {

	@Autowired
	UserDao dao;
	

	@Test
	public void testAdd() {
		User user = new User();
		user.setName("name");
		user.setPassword("password");
		List<Address> addresses = new ArrayList<Address>();
		Address e = new Address();
		e.setCity("city");
		e.setColony("colony");
		e.setExtNumber("extNumber");
		e.setIntNumber("intNumber");
		State state = new State();
		state.setState("state");
		e.setState(state);
		addresses.add(e);
		user.setAddress(addresses);
		user.setUsername("username");
		user.setCountry("country");
		user.setMail("mail");
		try {
			assertTrue("Cant insert", dao.add(user)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
	}

	@Test
	public void testGet() {
		User user = new User();
		user.setName("name");
		user.setPassword("password");
		List<Address> addresses = new ArrayList<Address>();
		Address e = new Address();
		e.setCity("city");
		e.setColony("colony");
		e.setExtNumber("extNumber");
		e.setIntNumber("intNumber");
		State state = new State();
		state.setState("state");
		e.setState(state);
		addresses.add(e);
		user.setAddress(addresses);
		user.setUsername("username");
		user.setCountry("country");
		user.setMail("mail");
		try {
			assertTrue("Cant insert", dao.add(user)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());
		}
		User found = new User();
		found = dao.get(user);
		assertTrue("User not found", user.getId().longValue() == found.getId().longValue());
	}

	@Test
	public void testGetById() {
		User user = new User();
		user.setName("name");
		user.setPassword("password");
		List<Address> addresses = new ArrayList<Address>();
		Address e = new Address();
		e.setCity("city");
		e.setColony("colony");
		e.setExtNumber("extNumber");
		e.setIntNumber("intNumber");
		State state = new State();
		state.setState("state");
		e.setState(state);
		addresses.add(e);
		user.setAddress(addresses);
		user.setUsername("username");
		user.setCountry("country");
		user.setMail("mail");
		try {
			assertTrue("Cant insert", dao.add(user)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());
		}
		User found = new User();
		found = dao.getById(user.getId());
		assertTrue("User not found", user.getId().longValue() == found.getId().longValue());
	}

	@Test
	public void testList() {
		User user = new User();
		user.setName("name");
		user.setPassword("password");
		List<Address> addresses = new ArrayList<Address>();
		Address e = new Address();
		e.setCity("city");
		e.setColony("colony");
		e.setExtNumber("extNumber");
		e.setIntNumber("intNumber");
		State state = new State();
		state.setState("state");
		e.setState(state);
		addresses.add(e);
		user.setAddress(addresses);
		user.setUsername("username");
		user.setCountry("country");
		user.setMail("mail");
		try {
			dao.add(user);
			dao.add(user);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());
		}
		List<User> results = dao.list(user);
		System.out.println("---"+results.size());
		assertTrue(results.size() == 2);
	}

	@Test
	public void testUpdate() {
		User user = new User();
		user.setName("name");
		user.setPassword("password");
		List<Address> addresses = new ArrayList<Address>();
		Address e = new Address();
		e.setCity("city");
		e.setColony("colony");
		e.setExtNumber("extNumber");
		e.setIntNumber("intNumber");
		State state = new State();
		state.setState("state");
		e.setState(state);
		addresses.add(e);
		user.setAddress(addresses);
		user.setUsername("username");
		user.setCountry("country");
		user.setMail("mail");
		try {
			assertTrue("Cant insert", dao.add(user)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
		user.setLastname("new Last name");
		user.setName("new Name");
		user.setPassword("new Password");
		try {
			dao.update(user);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
		User target = dao.get(user);
		assertTrue("update did not work", target.getName().equalsIgnoreCase(user.getName()));
	}

	@Test
	public void testDelete() {
		User user = new User();
		user.setName("name");
		user.setPassword("password");
		List<Address> addresses = new ArrayList<Address>();
		Address e = new Address();
		e.setCity("city");
		e.setColony("colony");
		e.setExtNumber("extNumber");
		e.setIntNumber("intNumber");
		State state = new State();
		state.setState("state");
		e.setState(state);
		addresses.add(e);
		user.setAddress(addresses);
		user.setUsername("username");
		user.setCountry("country");
		user.setMail("mail");
		try {
			assertTrue("Cant insert", dao.add(user)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());
		}
		User found = new User();
		found = dao.get(user);
		assertTrue("User not found", user.getId().longValue() == found.getId().longValue());
		dao.delete(user);
		try{
			found = dao.get(user);
		}catch(Exception ex){
			assertTrue("still exists", ex != null);
		}
	}



}
