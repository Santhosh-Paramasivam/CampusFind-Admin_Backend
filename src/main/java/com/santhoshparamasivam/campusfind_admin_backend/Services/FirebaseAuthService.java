package com.santhoshparamasivam.campusfind_admin_backend.Services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.santhoshparamasivam.campusfind_admin_backend.ServerErrorCodes;
import com.santhoshparamasivam.campusfind_admin_backend.ServerException;
import com.santhoshparamasivam.campusfind_admin_backend.Services.FirestoreService;
import io.grpc.Server;
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
        logger.info("Attempt to create user, email : " + email);

        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password);

            UserRecord userRecord = firebaseAuth.createUser(request);
            logger.info("Successfully created new user: " + userRecord.getUid());

            return userRecord.getUid();
        }
        catch (FirebaseAuthException e) {
            logger.error("User not created" + e.getMessage());

            throw new ServerException(ServerErrorCodes.FIREBASE_AUTH_ERROR,e.getMessage(),e.getHttpResponse().getStatusCode());
        }
    }

    public String extractAndVerifyIdToken(String authHeader) {
        String uid;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServerException(ServerErrorCodes.AUTH_TOKEN_MISSING,ServerErrorCodes.ERROR_MAP.get(ServerErrorCodes.AUTH_TOKEN_MISSING), HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        uid = verifyIdToken(token);

        return uid;
    }

    public String verifyIdToken(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            logger.info("Verified user UID: " + uid);

            return uid;
        } catch (FirebaseAuthException e) {
            logger.error("Invalid ID token: " + e.getMessage());

            throw new ServerException(ServerErrorCodes.INVALID_JWT_TOKEN, ServerErrorCodes.ERROR_MAP.get(ServerErrorCodes.INVALID_JWT_TOKEN),HttpStatus.UNAUTHORIZED);
        }
    }
}
