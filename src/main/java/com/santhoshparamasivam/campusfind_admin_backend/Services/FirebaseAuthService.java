package com.santhoshparamasivam.campusfind_admin_backend.Services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.santhoshparamasivam.campusfind_admin_backend.ServerException;
import com.santhoshparamasivam.campusfind_admin_backend.Services.FirestoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseAuthService {

    private final FirebaseAuth firebaseAuth;
    private final AdminService adminService;
    private final Logger logger = LoggerFactory.getLogger(FirebaseAuthService.class);
    FirebaseAuthService(FirebaseAuth firebaseAuth, FirestoreService firestoreService, AdminService adminService) {
        this.firebaseAuth = firebaseAuth;
        this.adminService = adminService;
    }

    public String createUser(String email, String password) throws FirebaseAuthException {
        logger.info("User create attempt email : " + email);

        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);

            UserRecord userRecord = firebaseAuth.createUser(request);
            logger.info("Successfully created new user: " + userRecord.getUid());

            return userRecord.getUid();
        }
        catch (FirebaseAuthException e) {
            logger.error("Invalid ID token: " + e.getMessage());
            return null;
        }
    }

    public String extractAndVerifyIdToken(String authHeader) {
        String uid;
        try {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServerException("auth-token-missing","JWT token for authentication not found in request header", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        uid = verifyIdToken(token);

        return uid;
    } catch (Exception e) {
            //To be improved
            throw new ServerException("generic error","generic message", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String verifyIdToken(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            logger.info("Verified user UID: " + uid);
            return uid;
        } catch (FirebaseAuthException e) {
            logger.error("❌ Invalid ID token: " + e.getMessage());
            return null;
        }
    }
}
