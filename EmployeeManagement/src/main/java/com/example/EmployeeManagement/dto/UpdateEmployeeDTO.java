package com.example.EmployeeManagement.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateEmployeeDTO {
   @Size(min = 2, max = 50, message = "Name must be between 2-50 characters")
    private String name;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @Pattern(regexp = "^\\+?[0-9\\-\\s]+$", message = "Invalid phone number")
    private String phone;
    
    private String address;
    
    private String department; // Optional: Add @Pattern for valid department names
    
    @PastOrPresent(message = "Join date cannot be in the future")
    private LocalDate joinDate;
}
    
