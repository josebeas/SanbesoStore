package com.sanbeso.dao.hibernate;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.sanbeso.dao.PostDao;
import com.sanbeso.domain.Post;
import com.sanbeso.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/testContext.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
public class PostDaoHibernateTest {

	@Autowired
	PostDao dao;
	
	@Test
	public void testAdd() {
		Post post = new Post();
		post.setName("name");
		User owner = new User();
		post.setOwner(owner);
		post.setContent("content");
		try{
			assertTrue("Cant insert ", dao.add(post)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGet() {
		Post post = new Post();
		post.setName("name");
		User owner = new User();
		post.setOwner(owner);
		post.setContent("content");
		try{
			assertTrue("Cant insert ", dao.add(post)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
		try{
			assertTrue("cant find ",dao.get(post).getId() == post.getId());
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetById() {
		Post post = new Post();
		post.setName("name");
		User owner = new User();
		post.setOwner(owner);
		post.setContent("content");
		try{
			assertTrue("Cant insert ", dao.add(post)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
		try{
			assertTrue("cant find ",dao.getById(post.getId()).getId() == post.getId());
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testSearch() {
		Post post = new Post();
		post.setName("name");
		User owner = new User();
		post.setOwner(owner);
		post.setContent("content");
		try{
			assertTrue("Cant insert ", dao.add(post)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
		try{
			Integer size = dao.search(post).size();
			System.out.println(size);
			assertTrue("cant find ",size > 0);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testDelete() {
		Post post = new Post();
		post.setName("name");
		User owner = new User();
		post.setOwner(owner);
		post.setContent("content");
		try{
			assertTrue("Cant insert ", dao.add(post)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
		try{
			dao.delete(post);
			Integer size = dao.search(post).size();
			System.out.println(size);
			assertTrue("cant delete ",size <= 0);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

	@Test
	public void testUpdate() {
		Post post = new Post();
		post.setName("name");
		User owner = new User();
		post.setOwner(owner);
		post.setContent("content");
		try{
			assertTrue("Cant insert ", dao.add(post)>0);
		}catch(Exception e){
			fail(e.getMessage());
		}
		try{
			post.setName("new Name");
			dao.update(post);
			Post found = dao.get(post);
			assertTrue("object are not updated",found.getName().equalsIgnoreCase(post.getName()));
		}catch(Exception e){
			fail(e.getMessage());
		}
	}

}
