package com.sintad.blog.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private LocalDateTime timestamp = LocalDateTime.now();
    private boolean error;
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return success(data, "");
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return load(false, 200, message, data);
    }

    public static <T> ApiResponse<T> success(T data, HttpStatus status) {
        return load(false, status.value(), status.getReasonPhrase(), data);
    }

    public static <T> ApiResponse<T> error(T data, HttpStatus status) {
        return error(data, status, null);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return error(null, status, message);
    }

    public static <T> ApiResponse<T> error(T data, HttpStatus status, String message) {
        return load(true, status.value(), message != null ? message : status.getReasonPhrase(), data);
    }

    public static <T> ApiResponse<T> load(boolean isError, int code, String message, T data) {
        return ApiResponse.<T>builder()
                .error(isError)
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

}