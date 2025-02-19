package com.santhoshparamasivam.campusfind_admin_backend.Services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.santhoshparamasivam.campusfind_admin_backend.FirestoreCollections;
import com.santhoshparamasivam.campusfind_admin_backend.FirestoreRepository;
import com.santhoshparamasivam.campusfind_admin_backend.Models.InstitutionAdmin;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class AdminService {
    FirestoreRepository firestoreRepository;
    AdminService(FirestoreRepository firestoreRepository){
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
}
