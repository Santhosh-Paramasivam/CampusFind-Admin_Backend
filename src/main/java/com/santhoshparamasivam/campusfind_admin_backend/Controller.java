package com.santhoshparamasivam.campusfind_admin_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class Controller {
    private final FirestoreService firestoreService;

    Controller(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    @GetMapping("/")
    String sayHello() throws ExecutionException, InterruptedException {
        firestoreService.addData();
        return "Hello";
    }
}
