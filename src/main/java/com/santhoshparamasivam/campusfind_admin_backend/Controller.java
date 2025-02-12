package com.santhoshparamasivam.campusfind_admin_backend;

import com.santhoshparamasivam.campusfind_admin_backend.Services.FirebaseAuthService;
import com.santhoshparamasivam.campusfind_admin_backend.Models.InstitutionMemberSchema;
import com.santhoshparamasivam.campusfind_admin_backend.Services.FirestoreService;
import com.santhoshparamasivam.campusfind_admin_backend.Services.InstitutionService;
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

    @GetMapping("/hello")
    public String sayHello()
    {
        logger.info("info");
        logger.debug("debug");
        logger.warn("warn");
        return "Hello";
    }

    @PostMapping("/protected")
    public String protectedEndpoint(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return "User logged in" + firebaseAuthService.extractAndVerifyIdToken(authHeader);
    }

    @PostMapping("/schema")
    public void addSchema() throws ExecutionException, InterruptedException {
        this.firestoreService.addMemberSchema();
    }

    @PostMapping("/register_admin")
    public ResponseEntity<Map<String, Object>> registerUser(
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password) {

        return firebaseAuthService.registerAdmin(email, username, password);
    }

    @PostMapping("/register_institution")
    void registerInstitution(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestParam String institutionName, @RequestParam String type,@RequestParam String contactEmail, @RequestParam String location,@RequestParam String address) throws ExecutionException, InterruptedException {
        String adminUid = firebaseAuthService.extractAndVerifyIdToken(authHeader);

        institutionService.createInstitution(adminUid, institutionName, type, contactEmail, location ,address);
    }

    @GetMapping("/institution_member_schema")
    InstitutionMemberSchema getInstitutionSchema(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws ExecutionException, InterruptedException {
       String uid = firebaseAuthService.extractAndVerifyIdToken(authHeader);
       return firestoreService.getMemberSchemaFromAdminUid(uid);
    }

}
