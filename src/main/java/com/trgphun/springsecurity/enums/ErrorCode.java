package com.trgphun.springsecurity.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_KEY(HttpStatus.BAD_REQUEST.value(), "Invalid key"),
    USERNAME_VALID(HttpStatus.BAD_REQUEST.value(), "Username must be at least {min} characters and at most {max} characters!"),
    PASSWORD_VALID(HttpStatus.BAD_REQUEST.value(), "Password must be at least {min} characters and at most {max} characters!"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "User not found!"),
    ROLE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "Role not found!"),
    PERMISSION_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "Permission not found!"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "Username or password is incorrect!"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), "Unauthenticated!"),
    SOMETHING_WENT_WRONG(HttpStatus.BAD_REQUEST.value(), "Something went wrong!"),
    USERNAME_UNREGISTERED(HttpStatus.BAD_REQUEST.value(), "Username is not registered in system!"),
    ;
    private final int code;
    private final String message;
}
