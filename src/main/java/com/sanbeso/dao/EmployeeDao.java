package com.sanbeso.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sanbeso.domain.Employee;
import com.sanbeso.exception.CustomValidationException;
/**
 * 
 * @author Jose Beas
 *
 */
@Repository("employeeDao")
public interface EmployeeDao {
	
	public Long add(Employee employee) throws CustomValidationException ;
	public Employee get(Employee employee);
	public Employee getById(Long employeeId);
	public List<Employee> search(Employee employee);
	public void delete(Employee employee) throws CustomValidationException;
	public void update(Employee employee) throws CustomValidationException;
	
}
