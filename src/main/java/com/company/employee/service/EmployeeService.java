package com.company.employee.service;

import java.util.List;

import com.company.employee.model.Employee;

public interface EmployeeService {
	
	Employee createEmployee(Employee employee);

    Employee getEmployeeById(String employeeId);
    
    List<Employee> getAllEmployee();

    List<Employee> searchEmployeesByAgeRange(int minAge, int maxAge);

    List<Employee> searchEmployeesByBirthYear(int birthYear);

    List<Employee> searchEmployeesByFirstName(String firstName);

    Employee updateEmployee(String employeeId, Employee updatedEmployee);

}
