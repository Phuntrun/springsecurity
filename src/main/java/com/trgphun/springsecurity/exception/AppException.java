package com.trgphun.springsecurity.exception;

import com.trgphun.springsecurity.enums.ErrorCode;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private final ErrorCode errorCode;

}
