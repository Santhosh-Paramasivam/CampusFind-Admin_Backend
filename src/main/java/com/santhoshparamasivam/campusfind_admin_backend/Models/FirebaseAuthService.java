package com.santhoshparamasivam.campusfind_admin_backend.Models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.santhoshparamasivam.campusfind_admin_backend.ServerException;
import com.santhoshparamasivam.campusfind_admin_backend.Services.FirestoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseAuthService {

    private FirebaseAuth firebaseAuth;
    private FirestoreService firestoreService;
    FirebaseAuthService(FirebaseAuth firebaseAuth, FirestoreService firestoreService) {
        this.firebaseAuth = firebaseAuth;
        this.firestoreService = firestoreService;
    }

    public String createUser(String email, String password) throws FirebaseAuthException {
        System.out.printf("email : " + email);
        System.out.printf("password : " + password);

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password);

        UserRecord userRecord = firebaseAuth.createUser(request);
        System.out.println("Successfully created new user: " + userRecord.getUid());

        return userRecord.getUid();
    }

    public static String verifyIdToken(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            System.out.println("✅ Verified user UID: " + uid);
            return uid;
        } catch (FirebaseAuthException e) {
            System.err.println("❌ Invalid ID token: " + e.getMessage());
            return null;
        }
    }

    public ResponseEntity<Map<String, Object>> registerUser(String email, String username, String password) {
        Map<String, Object> response = new HashMap<>();

        try {
            String uid = createUser(email, password);
            if (uid == null) {
                throw new ServerException("firebase-auth-error", "Email or Password is malformed", HttpStatus.BAD_REQUEST);
            }

            firestoreService.addInstitutionAdmins(uid, null, email, username);

            response.put("message", "User registered successfully");
            response.put("uid", uid);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch(FirebaseAuthException e){
            throw new ServerException("firebase-auth",e.getMessage(),e.getHttpResponse().getStatusCode());
        }
        catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", "firebase-auth-error",
                    "message", e.getMessage()
            ));
        }
    }

}
