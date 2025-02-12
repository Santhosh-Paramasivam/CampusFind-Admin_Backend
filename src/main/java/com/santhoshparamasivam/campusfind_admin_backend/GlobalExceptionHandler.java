package com.santhoshparamasivam.campusfind_admin_backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Map<String, Object>> handleServerException(ServerException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", ex.getErrorCode());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", ex.getStatus().value());
        errorResponse.put("error", ex.getStatus().getReasonPhrase());

        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("error", "Internal Server Error");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
