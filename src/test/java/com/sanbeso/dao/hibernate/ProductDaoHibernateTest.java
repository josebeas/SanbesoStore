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
import com.sanbeso.dao.ProductDao;
import com.sanbeso.domain.Brand;
import com.sanbeso.domain.Model;
import com.sanbeso.domain.Product;
import com.sanbeso.exception.CustomValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/testContext.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class ProductDaoHibernateTest {

	@Autowired
	ProductDao dao;
	

	@Test
	public void testAdd() {
		Product product = new Product();
		product.setAvailability(1);
		product.setDescripction("descripction");
		product.setSerial("serial");
		Brand brand = new Brand();
		brand.setDescription("description");
		brand.setName("name");
		product.setBrand(brand);
		Model model = new Model();
		model.setDescription("description");
		model.setName("name");
		product.setModel(model);
		try {
			assertTrue("Cant insert", dao.add(product)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
	}

	@Test
	public void testGet() {
		Product product = new Product();
		product.setAvailability(1);
		product.setDescripction("descripction");
		product.setSerial("serial");
		Brand brand = new Brand();
		brand.setDescription("description");
		brand.setName("name");
		product.setBrand(brand);
		Model model = new Model();
		model.setDescription("description");
		model.setName("name");
		product.setModel(model);
		try {
			assertTrue("Cant insert", dao.add(product)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
		Product found = new Product();
		found = dao.get(product);
		assertTrue("Product not found", product.getId().longValue() == found.getId().longValue());
	}

	@Test
	public void testGetById() {
		Product product = new Product();
		product.setAvailability(1);
		product.setDescripction("descripction");
		product.setSerial("serial");
		Brand brand = new Brand();
		brand.setDescription("description");
		brand.setName("name");
		product.setBrand(brand);
		Model model = new Model();
		model.setDescription("description");
		model.setName("name");
		product.setModel(model);
		try {
			assertTrue("Cant insert", dao.add(product)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
		Product found = new Product();
		found = dao.get(product);
		assertTrue("User not found", product.getId().longValue() == found.getId().longValue());
	}

	@Test
	public void testList() {
		Product product = new Product();
		product.setAvailability(1);
		product.setDescripction("descripction");
		product.setSerial("serial");
		Brand brand = new Brand();
		brand.setDescription("description");
		brand.setName("name");
		product.setBrand(brand);
		Model model = new Model();
		model.setDescription("description");
		model.setName("name");
		product.setModel(model);
		try {
			dao.add(product);
			dao.add(product);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
		List<Product> results = dao.list(product);
		assertTrue(results.size() == 2);
	}

	@Test
	public void testUpdate() {
		Product product = new Product();
		product.setAvailability(1);
		product.setDescripction("descripction");
		product.setSerial("serial");
		Brand brand = new Brand();
		brand.setDescription("description");
		brand.setName("name");
		product.setBrand(brand);
		Model model = new Model();
		model.setDescription("description");
		model.setName("name");
		product.setModel(model);
		try {
			assertTrue("Cant insert", dao.add(product)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
		product.setAvailability(2);
		product.setDescripction("new descripction");
		product.setSerial("new serial");
		try {
			dao.update(product);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
		Product target = dao.get(product);
		assertTrue("update did not work", target.getDescripction().equalsIgnoreCase(product.getDescripction()));
	}

	@Test
	public void testDelete() {
		Product product = new Product();
		product.setAvailability(1);
		product.setDescripction("descripction");
		product.setSerial("serial");
		Brand brand = new Brand();
		brand.setDescription("description");
		brand.setName("name");
		product.setBrand(brand);
		Model model = new Model();
		model.setDescription("description");
		model.setName("name");
		product.setModel(model);
		try {
			assertTrue("Cant insert", dao.add(product)>0);
		} catch (PersistenceException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		} catch (CustomValidationException ex) {
			fail(ex.getMessage());//e.printStackTrace();
		}
		Product found = new Product();
		found = dao.get(product);
		assertTrue("User not found", product.getId().longValue() == found.getId().longValue());
		dao.delete(product);
		try{
			found = dao.get(product);
		}catch(Exception ex){
			assertTrue("still exists", ex != null);
		}
	}



}
