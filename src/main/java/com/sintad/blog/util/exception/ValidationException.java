package com.sintad.blog.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class ValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus status;
    private final Object data;

    public ValidationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.data = null;
    }

    public ValidationException(String message, HttpStatus status, Object data) {
        super(message);
        this.status = status;
        this.data = data;
    }

}