package com.santhoshparamasivam.campusfind_admin_backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import java.util.concurrent.ExecutionException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Map<String, Object>> handleServerException(ServerException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error-code", ex.getErrorCode());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("error-status", ex.getStatus().getReasonPhrase());

        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<Map<String, String>> handleExecutionException(ExecutionException ex) {
        Throwable rootCause = ex.getCause();

        Map<String, String> response = new HashMap<>();
        response.put("error", "execution_error");
        response.put("message", rootCause != null ? rootCause.getMessage() : "Unknown execution error");

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (rootCause instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (rootCause instanceof TimeoutException) {
            status = HttpStatus.GATEWAY_TIMEOUT;
        }

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<Map<String, String>> handleInterruptedException(InterruptedException ex) {
        Thread.currentThread().interrupt();

        Map<String, String> response = new HashMap<>();
        response.put("error", "interrupted_error");
        response.put("message", "The operation was interrupted");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("error-status", "Internal Server Error");

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
