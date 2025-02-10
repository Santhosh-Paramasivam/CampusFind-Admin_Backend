package com.santhoshparamasivam.campusfind_admin_backend;

import com.google.firebase.auth.FirebaseAuthException;
import com.santhoshparamasivam.campusfind_admin_backend.Models.FirebaseAuthService;
import com.santhoshparamasivam.campusfind_admin_backend.Services.FirestoreService;
import com.santhoshparamasivam.campusfind_admin_backend.Services.InstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class Controller {
    private final FirestoreService firestoreService;
    private final FirestoreRepository firestoreRepository;

    private final InstitutionService institutionService;
    private final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final FirebaseAuthService firebaseAuthService;
    Controller(FirestoreService firestoreService, FirestoreRepository firestoreRepository, FirebaseAuthService firebaseAuthService, InstitutionService institutionService) {
        this.firestoreService = firestoreService;
        this.firestoreRepository = firestoreRepository;
        this.firebaseAuthService = firebaseAuthService;
        this.institutionService = institutionService;
    }

    @GetMapping("/")
    String root() throws ExecutionException, InterruptedException {
        return "Hello";
    }

    @GetMapping("/updateTest")
    String updateTest() {
        return "Hello";
    }

    @GetMapping("/query")
    String query() throws ExecutionException, InterruptedException {

        firestoreRepository.querySubcollectionDocument("admin_trial/abc/def");

        return "Query completed";
    }

    @PostMapping("/register_user")
    void registerUser(@RequestParam(required = false) String email,@RequestParam(required = false) String username, @RequestParam(required = false) String password) throws FirebaseAuthException, ExecutionException, InterruptedException {
        logger.info("email : " + email);
        logger.info("username : " + username);
        logger.info("password : " + password);

        String uid = null;
        try
        {
            uid = firebaseAuthService.createUser(email, password);
        }
        catch (FirebaseAuthException e)
        {
            logger.info(e.toString());
            return;
        }

        firestoreService.addInstitutionAdmins(uid, null, email, username);
    }

    @PostMapping("/register_institution")
    void registerInstitution(@RequestParam String institutionName, @RequestParam String type,@RequestParam String contactEmail, @RequestParam String location,@RequestParam String address) throws ExecutionException, InterruptedException {
        institutionService.createInstitution(institutionName, type, contactEmail, location ,address);
    }



}
