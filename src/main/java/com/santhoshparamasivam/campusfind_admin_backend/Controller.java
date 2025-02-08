package com.santhoshparamasivam.campusfind_admin_backend;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class Controller {
    private final FirestoreService firestoreService;
    private final FirestoreRepository firestoreRepository;

    private final FirebaseAuthService firebaseAuthService;
    Controller(FirestoreService firestoreService, FirestoreRepository firestoreRepository, FirebaseAuthService firebaseAuthService) {
        this.firestoreService = firestoreService;
        this.firestoreRepository = firestoreRepository;
        this.firebaseAuthService = firebaseAuthService;
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

    @GetMapping("/test_auth")
    String testAuth(@RequestParam String username, @RequestParam String password) throws FirebaseAuthException {
        firebaseAuthService.createUser(username, password);
        return "hello";
    }
}
