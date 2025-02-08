package com.santhoshparamasivam.campusfind_admin_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class Controller {
    private final FirestoreService firestoreService;
    private final FirestoreRepository firestoreRepository;

    Controller(FirestoreService firestoreService, FirestoreRepository firestoreRepository) {
        this.firestoreService = firestoreService;
        this.firestoreRepository = firestoreRepository;
    }

    @GetMapping("/")
    String sayHello() throws ExecutionException, InterruptedException {
        firestoreService.addData();
        return "Hello";
    }

    @GetMapping("/query")
    String query() throws ExecutionException, InterruptedException {

        firestoreRepository.querySubcollectionDocument("admin_trial/abc/def");

        return "Query completed";
    }
}
