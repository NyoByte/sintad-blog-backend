package com.sintad.blog.util.exception;


import com.sintad.blog.util.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ApiResponse<String>> handleConstraintViolation(ConstraintViolationException ex) {
//        return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage());
//    }
//
//    @ExceptionHandler(SecurityException.class)
//    public ResponseEntity<ApiResponse<String>> handleSecurityException(SecurityException ex) {
//        return ApiResponse.error(HttpStatus.FORBIDDEN, ex.getMessage());
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
//        return ApiResponse.error(HttpStatus.FORBIDDEN, ex.getMessage());
//    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleGlobalException(Exception ex) {
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ApiResponse<Object> handleValidationException(ValidationException ex) {
        return ApiResponse.error(ex.getData(), ex.getStatus(), ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatusCode status,
                                                                   WebRequest request) {
        ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.NOT_FOUND, "La ruta " + ex.getRequestURL() + " no fue encontrada");
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, "Method Argument Not Valid");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ApiResponse<Object> apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, "Invalid JSON format in the request");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}