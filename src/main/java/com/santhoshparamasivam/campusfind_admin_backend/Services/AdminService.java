package com.santhoshparamasivam.campusfind_admin_backend.Services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.auth.FirebaseAuthException;
import com.santhoshparamasivam.campusfind_admin_backend.FirestoreCollections;
import com.santhoshparamasivam.campusfind_admin_backend.FirestoreRepository;
import com.santhoshparamasivam.campusfind_admin_backend.Models.InstitutionAdmin;
import com.santhoshparamasivam.campusfind_admin_backend.ServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class AdminService {
    FirestoreRepository firestoreRepository;
    FirebaseAuthService firebaseAuthService;
    AdminService(FirestoreRepository firestoreRepository, FirebaseAuthService firebaseAuthService){
        this.firebaseAuthService = firebaseAuthService;
        this.firestoreRepository = firestoreRepository;
    }

    public void updateInstitutionAdmins(String documentId, String field, String value) throws ExecutionException, InterruptedException {
        firestoreRepository.updateDocumentFromField(FirestoreCollections.INSTITUTION_ADMINS, documentId, "institutionId", "");
    }

    public void addInstitutionAdmins(String documentId, String institutionId, String emailId, String username) throws ExecutionException, InterruptedException {
        InstitutionAdmin institutionAdmin = new InstitutionAdmin(documentId, emailId, institutionId, username);

        firestoreRepository.createDocumentFromObjectAndDocumentId(FirestoreCollections.INSTITUTION_ADMINS, documentId, institutionAdmin);
    }

    public String getInstitutionIdFromAdminUid(String uid) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot =  firestoreRepository.queryByDocumentId(FirestoreCollections.INSTITUTION_ADMINS, uid);

        return documentSnapshot.getString("institution_id");
    }

    public ResponseEntity<Map<String, Object>> registerAdmin(String email, String username, String password) {
        Map<String, Object> response = new HashMap<>();

        try {
            String uid = firebaseAuthService.createUser(email, password);
            if (uid == null) {
                throw new ServerException("firebase-auth-error", "Email or Password is malformed", HttpStatus.BAD_REQUEST);
            }

            addInstitutionAdmins(uid, null, email, username);

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
