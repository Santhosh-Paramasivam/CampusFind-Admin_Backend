package com.santhoshparamasivam.campusfind_admin_backend;

import com.google.firebase.auth.FirebaseAuthException;
import com.santhoshparamasivam.campusfind_admin_backend.Models.FirebaseAuthService;
import com.santhoshparamasivam.campusfind_admin_backend.Services.FirestoreService;
import com.santhoshparamasivam.campusfind_admin_backend.Services.InstitutionService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {
    private final FirestoreService firestoreService;

    private final InstitutionService institutionService;
    private final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final FirebaseAuthService firebaseAuthService;
    Controller(FirestoreService firestoreService, FirebaseAuthService firebaseAuthService, InstitutionService institutionService) {
        this.firestoreService = firestoreService;
        this.firebaseAuthService = firebaseAuthService;
        this.institutionService = institutionService;
    }

    @PostMapping("/protected")
    public String protectedEndpoint(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return "Missing or invalid Authorization header";
            }

            String token = authHeader.substring(7);
            String uid = FirebaseAuthService.verifyIdToken(token);

            return "Hello, user " + uid + "! You are authenticated.";
        } catch (Exception e) {
            return "Unauthorized: " + e.getMessage();
        }
    }

    @PostMapping("/schema")
    public void addSchema() throws ExecutionException, InterruptedException {
        this.firestoreService.addMemberSchema();
    }

    @PostMapping("/register_user")
    public ResponseEntity<Map<String, Object>> registerUser(
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password) {

        return firebaseAuthService.registerUser(email, username, password);
    }

    @PostMapping("/register_institution")
    void registerInstitution(@RequestParam String institutionName, @RequestParam String type,@RequestParam String contactEmail, @RequestParam String location,@RequestParam String address) throws ExecutionException, InterruptedException {
        institutionService.createInstitution(institutionName, type, contactEmail, location ,address);
    }



}
