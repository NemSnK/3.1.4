package ru.nemkov.springbootcrud.exeption_handling;

public class ValidationUserException extends RuntimeException{
    public ValidationUserException(String message) { super(message); }
}
