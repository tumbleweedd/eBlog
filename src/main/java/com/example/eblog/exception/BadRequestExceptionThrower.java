package com.example.eblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestExceptionThrower extends ExceptionThrower {
    private static HttpStatus status = HttpStatus.BAD_REQUEST;

    public static void throwUserExistsException() {
        throw new ResponseStatusException(status, "User exist!");
    }
    public static void throwDifferentPassException() {
        throw new ResponseStatusException(status, "The passwords must be different!");
    }
    public static void throwBreachedPassException() {
        throw new ResponseStatusException(status, "The password is in the hacker's database!");
    }
    public static void throwPasswordLengthException() {
        throw new ResponseStatusException(status, "Password length must be 12 chars minimum!");
    }
    public static void throwUerNotFoundException() {
        throw new ResponseStatusException(status, "User not found!");
    }
    public static void throwUnMatchingPasswords() {
        throw new ResponseStatusException(status, "Пароли должны совпадать");
    }

    public static void throwPostNotFoundException() {
        throw new ResponseStatusException(status,"Post not found");
    }
}

