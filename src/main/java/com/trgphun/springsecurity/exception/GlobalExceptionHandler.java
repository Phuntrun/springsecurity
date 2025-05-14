package com.trgphun.springsecurity.exception;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.trgphun.springsecurity.enums.ErrorCode;

import jakarta.validation.ConstraintViolation;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> appExceptionHandler(AppException e) {
        return ResponseEntity.status(e.getErrorCode().getCode()).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(e.getFieldError().getDefaultMessage());

            var constraintViolation = e.getBindingResult().getAllErrors()
                    .get(0).unwrap(ConstraintViolation.class);
            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
        } catch (IllegalArgumentException ex) {

        }
        return ResponseEntity.badRequest().body(
            Objects.nonNull(attributes) 
            ? mapAttribute(errorCode.getMessage(), attributes)
            : errorCode.getMessage()
        );
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get("min"));
        String maxValue = String.valueOf(attributes.get("max"));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue)
                .replace("{" + MAX_ATTRIBUTE + "}", maxValue);
    }
}
