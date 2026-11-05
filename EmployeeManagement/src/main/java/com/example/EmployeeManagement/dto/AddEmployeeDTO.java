package com.example.EmployeeManagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AddEmployeeDTO {
  
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2-50 characters")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @Pattern(regexp = "^\\+?[0-9]{1,3}-\\s?[0-9]{6,15}$", message = "Invalid phone number")
    private String phone;
    
    private String address;
    
    @NotBlank(message = "Department is required")
    private String department;
    
    @NotNull(message = "Join date is required")
    private LocalDate joinDate;
}