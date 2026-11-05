package com.example.EmployeeManagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeManagement.dto.AddEmployeeDTO;
import com.example.EmployeeManagement.dto.UpdateEmployeeDTO;
import com.example.EmployeeManagement.exception.ResourceNotFoundException;
import com.example.EmployeeManagement.model.Department;
import com.example.EmployeeManagement.model.Employee;
import com.example.EmployeeManagement.response.ApiResponse;
import com.example.EmployeeManagement.service.EmployeeInterface;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequestMapping("/api/employees")
@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class EmployeeController {
    private final EmployeeInterface employeeInterface;

    public EmployeeController(EmployeeInterface employeeInterface){
        this.employeeInterface=employeeInterface;
    }

    @GetMapping("/employee")
    public String getEmployeeData() {
        return "Employee data: [confidential]";
    }
    
    @GetMapping("/find/all")
    public ResponseEntity<ApiResponse> getAllEmployees(){
        List<Employee> employees=employeeInterface.getAllEmployees();
        return ResponseEntity.ok(new ApiResponse("Success", employees));   
    }
    @GetMapping("/find/byId/{eid}")
    public ResponseEntity<ApiResponse> getEmployeeById(@PathVariable Long eid){
       try{
            Employee employees=employeeInterface.getEmployeeById(eid);
            return ResponseEntity.ok(new ApiResponse("Employee Found by name", employees));

        }
       catch(Exception e){
         e.printStackTrace();  // This will print the exception details to the console
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Error: " + e.getMessage(), null));
       }
    }
    @GetMapping("/find/byName/search")
    public ResponseEntity<ApiResponse> getEmployeeByName(@RequestParam String name){
       try{
            List<Employee> employees=employeeInterface.findEmployeeByName(name);
            if(employees.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Employee Not Found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Employee Found by name", employees));

        }
       catch(Exception e){
         e.printStackTrace();  // This will print the exception details to the console
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Error: " + e.getMessage(), null));
       }
    }

    @GetMapping("/find/byDepartment/search")
    public ResponseEntity<ApiResponse> getEmployeeByDepartment(@RequestParam Department department){
       try{
            List<Employee> employees=employeeInterface.filterByDepartment(department);
            if(employees.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Employee Not Found!", null));
            }
            return ResponseEntity.ok(new ApiResponse("Employee Found by name", employees));

        }
       catch(Exception e){
        return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
       }
    }

    @DeleteMapping("/delete/{eid}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long eid){
        try{
            employeeInterface.deleteEmployee(eid);
            return ResponseEntity.ok(new ApiResponse("Employee Deleted Success!",eid));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addEmployee(@Valid @RequestBody AddEmployeeDTO employeeDTO){
        try{
            Employee employees=employeeInterface.createEmployee(employeeDTO);
            return ResponseEntity.ok(new ApiResponse("Employee Added Success!",employees));
        }
        catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/update/{eid}")
    public ResponseEntity<ApiResponse> updateEmployee(@PathVariable Long eid,@Valid @RequestBody UpdateEmployeeDTO employeeDTO){
        try{
            Employee employees=employeeInterface.updateEmployee(eid, employeeDTO);
            return ResponseEntity.ok(new ApiResponse("Employee Updated Success!",employees));
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    
    
}
