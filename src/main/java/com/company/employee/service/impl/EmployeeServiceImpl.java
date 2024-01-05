package com.company.employee.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.employee.model.Employee;
import com.company.employee.repository.EmployeeRepository;
import com.company.employee.service.EmployeeService;


@Service
public class EmployeeServiceImpl implements EmployeeService {
	
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	private static int currentId = 10000000;

	@Autowired
    private EmployeeRepository employeeRepository;

	@Override
    public Employee createEmployee(Employee employee) {
        try {
            logger.debug("User {} is creating a new employee.", employee.getFirstName()+" "+employee.getLastName());
            employee.setEmployeeId("P" + generateUniqueId());
            Employee savedEmployee = employeeRepository.save(employee);
            logger.info("Employee created successfully with ID: {} by user: {}", savedEmployee.getEmployeeId());

            return savedEmployee;
        } catch (Exception e) {
            logger.error("Error creating employee", e);
            throw new RuntimeException("Error creating employee", e);
        }
    }

    @Override
    public Employee updateEmployee(String employeeId, Employee updatedEmployee) {
    	try {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmployeeId(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            if ("Male".equalsIgnoreCase(existingEmployee.getGender())) {
                updateMaleEmployeeDetails(existingEmployee, updatedEmployee);
            } else if ("Female".equalsIgnoreCase(existingEmployee.getGender())) {
                updateFemaleEmployeeDetails(existingEmployee, updatedEmployee);
            }
            return employeeRepository.save(existingEmployee);
        }
        return null; 
    	}
    	catch (Exception e) {
            logger.error("Error updating employee with ID: {}", employeeId, e);
            throw new RuntimeException("Error updating employee with ID: " + employeeId, e);
        }
    }

    private void updateMaleEmployeeDetails(Employee existingEmployee, Employee updatedEmployee) {
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setDesignation(updatedEmployee.getDesignation());
        existingEmployee.setMaritalStatus(updatedEmployee.getMaritalStatus());
    }

    private void updateFemaleEmployeeDetails(Employee existingEmployee, Employee updatedEmployee) {
        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setDesignation(updatedEmployee.getDesignation());
        existingEmployee.setMaritalStatus(updatedEmployee.getMaritalStatus());
    }
    
    private static synchronized int generateUniqueId() {
        return ++currentId;
    }
    
    @Override
    public Employee getEmployeeById(String employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmployeeId(employeeId);
        return optionalEmployee.orElse(null);
    }
    
    @Override
	public List<Employee> getAllEmployee() {
		List<Employee> employees = employeeRepository.findAll();
        return employees;
	}

    @Override
    public List<Employee> searchEmployeesByAgeRange(int minAge, int maxAge) {
        return employeeRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public List<Employee> searchEmployeesByBirthYear(int birthYear) {
        return employeeRepository.findByBirthYear(birthYear);
    }

    @Override
    public List<Employee> searchEmployeesByFirstName(String firstName) {
        return employeeRepository.findByFirstNameIgnoreCaseContaining(firstName);
    }
}
