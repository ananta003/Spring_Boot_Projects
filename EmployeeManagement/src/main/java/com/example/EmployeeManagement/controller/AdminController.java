package com.example.EmployeeManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.EmployeeManagement.dto.AuthRequestDTO;

import jakarta.servlet.http.HttpServletRequest;


@RequestMapping("/api/admin")
@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AdminController{

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO credentials,HttpServletRequest request) {
       try{
            UsernamePasswordAuthenticationToken authenticationToken=new 
              UsernamePasswordAuthenticationToken(credentials.getUsername(),credentials.getPassword());

            Authentication authentication=authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            request.getSession(true);
            return ResponseEntity.ok().build();
       }
       catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}