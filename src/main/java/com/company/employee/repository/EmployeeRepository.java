package com.company.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.employee.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findByEmployeeId(String employeeId);

    List<Employee> findByAgeBetween(int minAge, int maxAge);

    List<Employee> findByBirthYear(int birthYear);

    List<Employee> findByFirstNameIgnoreCaseContaining(String firstName);

}
