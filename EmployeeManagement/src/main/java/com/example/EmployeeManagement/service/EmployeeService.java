package com.example.EmployeeManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.EmployeeManagement.dto.AddEmployeeDTO;
import com.example.EmployeeManagement.dto.UpdateEmployeeDTO;
import com.example.EmployeeManagement.exception.EmployeeAlreadyExistsException;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.model.Department;
import com.example.EmployeeManagement.model.Employee;
import com.example.EmployeeManagement.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService implements EmployeeInterface{

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long eid) {
        return employeeRepository.findById(eid).orElseThrow(()->new ResourceNotFoundException("Employee Not Found"));    
    }

    @Transactional
    @Override
    public Employee createEmployee(AddEmployeeDTO employeeDTO) {
        
        if(employeeRepository.existsByEmail(employeeDTO.getEmail())){
            throw new EmployeeAlreadyExistsException("Email Already Exists");
        }
       
        Employee employee=new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhone(employeeDTO.getPhone());
        employee.setAddress(employeeDTO.getAddress());
        employee.setDepartment(Department.valueOf(employeeDTO.getDepartment().toUpperCase()));
        employee.setJoinDate(employeeDTO.getJoinDate());

        return employeeRepository.save(employee);
    }
    
    @Transactional
    @Override
    public Employee updateEmployee(Long eid, UpdateEmployeeDTO employeeDTO) {
        Employee employee=employeeRepository.findById(eid)
                            .orElseThrow(()->new ResourceNotFoundException("Employee Not Found!"));
        if(employeeDTO.getEmail()!=null&&!employeeDTO.getEmail().equals(employee.getEmail())
            &&employeeRepository.existsByEmail(employeeDTO.getEmail())){
                throw new EmployeeAlreadyExistsException("Email Already Exists!");
        }
        if(employeeDTO.getName()!=null){
            employee.setName(employeeDTO.getName());
        }
        if(employeeDTO.getEmail() != null){
            employee.setEmail(employeeDTO.getEmail());
        }
        if(employeeDTO.getPhone() != null){
            employee.setPhone(employeeDTO.getPhone());
        }
        if(employeeDTO.getAddress() != null){
            employee.setAddress(employeeDTO.getAddress());
        }
        if(employeeDTO.getDepartment() != null){
            employee.setDepartment(Department.valueOf(employeeDTO.getDepartment().toUpperCase()));
        }
        if(employeeDTO.getJoinDate() != null){
            employee.setJoinDate(employeeDTO.getJoinDate());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long eid) {
        employeeRepository.findById(eid)
            .ifPresentOrElse(employeeRepository::delete, 
            ()->{throw new ResourceNotFoundException("Employee Not Found!");});
    }

    @Override
    public List<Employee> findEmployeeByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Employee> filterByDepartment(Department department) {
       return employeeRepository.findByDepartment(department);
    }  
}
