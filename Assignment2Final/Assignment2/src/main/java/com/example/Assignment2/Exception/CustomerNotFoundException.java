package com.example.Assignment2.Exception;

public class CustomerNotFoundException  extends RuntimeException{
    public CustomerNotFoundException(Integer id) {
        super("Could not find customer " + id);
    }
}
