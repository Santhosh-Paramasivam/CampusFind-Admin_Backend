package com.santhoshparamasivam.campusfind_admin_backend.Services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuthException;
import com.santhoshparamasivam.campusfind_admin_backend.*;
import com.santhoshparamasivam.campusfind_admin_backend.Models.InstitutionAdmin;
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
        HashMap<String, Object> response = new HashMap<>();

        try {
            String uid = firebaseAuthService.createUser(email, password);
            if (uid == null) {
                throw new ServerException(ServerErrorCodes.FIREBASE_AUTH_ERROR, "Email or Password is malformed", HttpStatus.BAD_REQUEST);
            }

            addInstitutionAdmins(uid, null, email, username);

            response.put("uid", uid);

            return ServerResponse.respond(ServerResponseCodes.USER_REGISTERED,ServerResponseCodes.RESPONSE_MAP.get(ServerResponseCodes.USER_REGISTERED), HttpStatus.CREATED, response);
        }
        catch(FirebaseAuthException e){
            throw new ServerException(ServerErrorCodes.FIREBASE_AUTH_ERROR,e.getMessage(),e.getHttpResponse().getStatusCode());
        }
        catch (ExecutionException | InterruptedException e) {
            throw new ServerException(ServerErrorCodes.INTERNAL_SERVER_ERROR,ServerErrorCodes.ERROR_MAP.get(ServerErrorCodes.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
