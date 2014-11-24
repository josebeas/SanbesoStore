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
import com.sanbeso.dao.BrandDao;
import com.sanbeso.domain.Brand;
import com.sanbeso.exception.CustomValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/testContext.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class BrandDaoHibernateTest {

	@Autowired
	BrandDao dao;
	

	@Test
	public void testAdd() {
		Brand brand = new Brand();
		brand.setName("name");
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
		Brand brand = new Brand();
		brand.setName("name");
		brand.setDescription("description");
		try {
			assertTrue("Cant insert", dao.add(brand)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());
		}
		Brand found = new Brand();
		found = dao.get(brand);
		assertTrue("Brand not found", brand.getId().longValue() == found.getId().longValue());
	}

	@Test
	public void testGetById() {
		Brand brand = new Brand();
		brand.setName("name");
		brand.setDescription("description");
		try {
			assertTrue("Cant insert", dao.add(brand)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());
		}
		Brand found = new Brand();
		found = dao.getById(brand.getId());
		assertTrue("User not found", brand.getId().longValue() == found.getId().longValue());
	}

	@Test
	public void testList() {
		Brand brand = new Brand();
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
		List<Brand> results = dao.search(brand);
		System.out.println("---"+results.size());
		assertTrue(results.size() == 2);
	}

	@Test
	public void testUpdate() {
		Brand brand = new Brand();
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
		Brand target = dao.get(brand);
		assertTrue("update did not work", target.getName().equalsIgnoreCase(brand.getName()));
	}

	@Test
	public void testDelete() {
		Brand brand = new Brand();
		brand.setName("name");
		brand.setDescription("description");
		try {
			assertTrue("Cant insert", dao.add(brand)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());
		}
		Brand found = new Brand();
		found = dao.get(brand);
		assertTrue("Brand not found", brand.getId().longValue() == found.getId().longValue());
		dao.delete(brand);
		try{
			found = dao.get(brand);
		}catch(Exception ex){
			assertTrue("still exists", ex != null);
		}
	}



}
