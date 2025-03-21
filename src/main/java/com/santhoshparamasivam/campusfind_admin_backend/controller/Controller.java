package com.santhoshparamasivam.campusfind_admin_backend.controller;

import com.santhoshparamasivam.campusfind_admin_backend.service.*;
import com.santhoshparamasivam.campusfind_admin_backend.model.InstitutionMemberSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {

    private final InstitutionService institutionService;
    private final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final FirebaseAuthService firebaseAuthService;
    private final InstitutionSchemaService institutionMemberSchemaService;
    private final AdminService adminService;
    Controller(FirebaseAuthService firebaseAuthService, InstitutionService institutionService, AdminService adminService, InstitutionSchemaService institutionMemberSchemaService) {
        this.firebaseAuthService = firebaseAuthService;
        this.institutionService = institutionService;
        this.adminService = adminService;
        this.institutionMemberSchemaService = institutionMemberSchemaService;
    }

    @PostMapping("/register_admin")
    public ResponseEntity<Map<String, Object>> registerUser(
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password) throws ExecutionException, InterruptedException {

        return adminService.registerAdmin(email, username, password);
    }

    @PostMapping("/register_institution")
    void registerInstitution(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestParam String institutionName, @RequestParam String type,@RequestParam String contactEmail, @RequestParam String location,@RequestParam String address) throws ExecutionException, InterruptedException {
        String adminUid = firebaseAuthService.extractAndVerifyIdToken(authHeader);

        institutionService.createInstitution(adminUid, institutionName, type, contactEmail, location ,address);
    }

    @GetMapping("/institution_member_schema")
    InstitutionMemberSchema getInstitutionSchema(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws ExecutionException, InterruptedException {
       String uid = firebaseAuthService.extractAndVerifyIdToken(authHeader);
       return institutionMemberSchemaService.getMemberSchemaFromAdminUid(uid);
    }

}
