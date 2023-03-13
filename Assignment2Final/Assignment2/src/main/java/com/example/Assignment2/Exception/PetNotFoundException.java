package com.example.Assignment2.Exception;


public class PetNotFoundException  extends RuntimeException{
    public PetNotFoundException(Integer id) {
        super("Could not find pet" + id);
    }
}