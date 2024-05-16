package com.example.se2Assignment.service;

public class MovieNotFoundException extends Throwable{
    public MovieNotFoundException(String message) {
        super(message);
    }
}
