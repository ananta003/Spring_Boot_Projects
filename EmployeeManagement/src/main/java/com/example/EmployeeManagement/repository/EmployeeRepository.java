package com.example.EmployeeManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeManagement.model.Department;
import com.example.EmployeeManagement.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
    boolean existsByEmail(String email);
    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByDepartment(Department department);
}