package com.example.advanced_webapp.Exceptions;

public class EmailExistException extends Exception{
    public EmailExistException(String message) {
        super(message);
    }
}
