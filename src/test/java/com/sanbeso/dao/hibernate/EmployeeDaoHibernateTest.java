package com.sanbeso.dao.hibernate;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.sanbeso.dao.EmployeeDao;
import com.sanbeso.domain.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/testContext.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class EmployeeDaoHibernateTest {

	@Autowired
	EmployeeDao dao;
	
	@Test
	public void testAdd() {
		Employee employee = new Employee();
		employee.setEmail("email");
		employee.setFirstName("firstName");
		employee.setEmail("lastName");
		try{
			assertTrue("cant insert", dao.add(employee)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGet() {
		Employee employee = new Employee();
		employee.setEmail("email");
		employee.setFirstName("firstName");
		employee.setEmail("lastName");
		try{
			assertTrue("cant insert", dao.add(employee)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
		try{
			assertTrue("aren't equals ", dao.get(employee).getId() == employee.getId());
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetById() {
		Employee employee = new Employee();
		employee.setEmail("email");
		employee.setFirstName("firstName");
		employee.setEmail("lastName");
		try{
			assertTrue("cant insert", dao.add(employee)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
		try{
			assertTrue("arent equals", dao.getById(employee.getId()).getId() == employee.getId());
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testSearch() {
		Employee employee = new Employee();
		employee.setEmail("email");
		employee.setFirstName("firstName");
		employee.setEmail("lastName");
		try{
			assertTrue("cant insert", dao.add(employee)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
		try{
			List<Employee> results =  dao.search(employee);
			assertTrue("the search did not match", results.size() == 1);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testDelete() {
		Employee employee = new Employee();
		employee.setEmail("email");
		employee.setFirstName("firstName");
		employee.setEmail("lastName");
		try{
			assertTrue("cant insert", dao.add(employee)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
		try{
			dao.delete(employee);
			List<Employee> results =  dao.search(employee);
			assertTrue("object found", results.size() == 0);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdate() {
		Employee employee = new Employee();
		employee.setEmail("email");
		employee.setFirstName("firstName");
		employee.setEmail("lastName");
		try{
			assertTrue("cant insert", dao.add(employee)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
		try{
			employee.setEmail("new email");
			dao.update(employee);
			Employee found =  dao.get(employee);
			assertTrue("object not updated", found.getEmail().equalsIgnoreCase(employee.getEmail()));
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

}
