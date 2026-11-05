package com.example.EmployeeManagement.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
     
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long eid;
    @NotBlank(message = "Name is Required")
    @Size(min = 2,max = 50,message = "Name must be 2-50 characters")
    private String name;

    @NotBlank(message = "Email is Required")
    @Email(message = "invalid email format")
    @Column(unique = true)
    private String email;
    
    @Pattern(regexp = "^\\+?[0-9]{1,3}-\\s?[0-9]{6,15}$", message = "Invalid phone number")
    private String phone;
    private String address;
    
    @Enumerated(EnumType.STRING)
    private Department department;

    private LocalDate joinDate;
}
