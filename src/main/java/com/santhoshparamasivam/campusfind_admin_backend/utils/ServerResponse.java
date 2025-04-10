package com.santhoshparamasivam.campusfind_admin_backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ServerResponse {

    // Do we need custom response-codes when http's is good enough?
    public static ResponseEntity<Map<String, Object>> respond(String responseCode, String message, HttpStatus status) {
       Map<String, Object> response = new HashMap<>();
       response.put("response-code",responseCode);
       response.put("message",message);
       response.put("status", status);

       return ResponseEntity.status(status).body(response);
    }
    public static ResponseEntity<Map<String, Object>> respond(String responseCode, String message, HttpStatus status, HashMap<String,Object> body) {
        Map<String, Object> response = new HashMap<>();
        response.put("response-code",responseCode);
        response.put("message",message);
        body.forEach((key,value) -> response.put(key,value));
        response.put("status", status);

        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<Map<String,Object>> respond(HttpStatus status, Map<String, Object> body) {
        return ResponseEntity.status(status).body(body);
    }

    public static ResponseEntity<Object> respond(HttpStatus status, Object body) {
        return ResponseEntity.status(status).body(body);
    }
}
