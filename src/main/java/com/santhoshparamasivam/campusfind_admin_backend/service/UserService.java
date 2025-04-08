package com.santhoshparamasivam.campusfind_admin_backend.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.santhoshparamasivam.campusfind_admin_backend.repository.FirestoreRepository;
import com.santhoshparamasivam.campusfind_admin_backend.utils.FirestoreCollections;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    private final FirestoreRepository firestoreRepository;
    private final FirebaseAuthService firebaseAuthService;

    public UserService(FirestoreRepository firestoreRepository, FirebaseAuthService firebaseAuthService) {
       this.firestoreRepository = firestoreRepository;
       this.firebaseAuthService = firebaseAuthService;
    }

    public void addUser(HashMap<String, Object> userDetails) throws ExecutionException, InterruptedException, FirebaseAuthException {

        String authId = firebaseAuthService.createUser((String) userDetails.get("email_id"),(String) userDetails.get("password"));

        userDetails.remove("password");
        userDetails.put("id",authId);

        firestoreRepository.createDocumentFromHashMap(FirestoreCollections.INSTITUTION_MEMBERS, userDetails);
    }
}
