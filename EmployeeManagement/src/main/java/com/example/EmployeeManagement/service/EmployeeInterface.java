package com.example.EmployeeManagement.service;

import java.util.List;

import com.example.EmployeeManagement.dto.AddEmployeeDTO;
import com.example.EmployeeManagement.dto.UpdateEmployeeDTO;
import com.example.EmployeeManagement.model.Department;
import com.example.EmployeeManagement.model.Employee;


public interface EmployeeInterface {
    
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long eid);
    Employee createEmployee(AddEmployeeDTO employee);
    Employee updateEmployee(Long eid, UpdateEmployeeDTO employee);
    void deleteEmployee(Long eid);
    List<Employee> findEmployeeByName(String name);
    List<Employee> filterByDepartment(Department department);    
}
