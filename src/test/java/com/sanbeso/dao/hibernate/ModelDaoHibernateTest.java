package com.sanbeso.dao.hibernate;

import static org.junit.Assert.*;

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
import com.sanbeso.dao.ModelDao;
import com.sanbeso.domain.Model;
import com.sanbeso.exception.CustomValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/testContext.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class ModelDaoHibernateTest {

	@Autowired
	ModelDao dao;
	

	@Test
	public void testAdd() {
		Model brand = new Model();
		brand.setName(null);
		brand.setDescription("description");
		try {
			assertTrue("Cant insert", dao.add(brand)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
	}

	@Test
	public void testGet() {
		Model brand = new Model();
		brand.setName("name");
		brand.setDescription("description");
		try {
			assertTrue("Cant insert", dao.add(brand)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());
		}
		Model found = new Model();
		found = dao.get(brand);
		assertTrue("Model not found", brand.getId().longValue() == found.getId().longValue());
	}

	@Test
	public void testGetById() {
		Model brand = new Model();
		brand.setName("name");
		brand.setDescription("description");
		try {
			assertTrue("Cant insert", dao.add(brand)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());
		}
		Model found = new Model();
		found = dao.getById(brand.getId());
		assertTrue("User not found", brand.getId().longValue() == found.getId().longValue());
	}

	@Test
	public void testList() {
		Model brand = new Model();
		brand.setName("name");
		brand.setDescription("description");
		try {
			dao.add(brand);
			dao.add(brand);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());
		}
		List<Model> results = dao.search(brand);
		System.out.println("---"+results.size());
		assertTrue(results.size() == 2);
	}

	@Test
	public void testUpdate() {
		Model brand = new Model();
		brand.setName("name");
		brand.setDescription("description");
		try {
			assertTrue("Cant insert", dao.add(brand)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
		brand.setDescription("new description");
		brand.setName("new description");
		try {
			dao.update(brand);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
		Model target = dao.get(brand);
		assertTrue("update did not work", target.getName().equalsIgnoreCase(brand.getName()));
	}

	@Test
	public void testDelete() {
		Model brand = new Model();
		brand.setName("name");
		brand.setDescription("description");
		try {
			assertTrue("Cant insert", dao.add(brand)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());
		}
		Model found = new Model();
		found = dao.get(brand);
		assertTrue("Model not found", brand.getId().longValue() == found.getId().longValue());
		dao.delete(brand);
		try{
			found = dao.get(brand);
		}catch(Exception ex){
			assertTrue("still exists", ex != null);
		}
	}



}
