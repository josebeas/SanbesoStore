package com.sanbeso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanbeso.domain.Employee;

@Controller
@RequestMapping("/employee/add")
public class EmployeeController {

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response){
		Employee employee = new Employee();
		String firtsName = request.getParameter("fistName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		employee.setEmail(email);
		employee.setLastName(lastName);
		employee.setFirstName(firtsName);
		return new ModelAndView("employeesuccess", "employee",employee);
	}
	
	
	
}
