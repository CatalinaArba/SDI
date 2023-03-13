package com.example.Assignment2.Exception;


public class AdoptionNotFoundException  extends RuntimeException{
    public AdoptionNotFoundException(Integer id) {
        super("Could not find adoption " + id);
    }
}
