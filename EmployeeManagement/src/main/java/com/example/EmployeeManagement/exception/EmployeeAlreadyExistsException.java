package com.example.EmployeeManagement.exception;

public class EmployeeAlreadyExistsException extends RuntimeException{
    public EmployeeAlreadyExistsException(String message){
        super(message);
    }
}
