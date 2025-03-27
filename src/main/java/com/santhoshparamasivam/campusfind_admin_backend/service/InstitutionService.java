package com.santhoshparamasivam.campusfind_admin_backend.service;

import com.google.cloud.firestore.DocumentSnapshot;
import com.santhoshparamasivam.campusfind_admin_backend.model.InstitutionAdmin;
import com.santhoshparamasivam.campusfind_admin_backend.utils.FirestoreCollections;
import com.santhoshparamasivam.campusfind_admin_backend.repository.FirestoreRepository;
import com.santhoshparamasivam.campusfind_admin_backend.model.Institution;
import com.santhoshparamasivam.campusfind_admin_backend.utils.ValidationConstants;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class InstitutionService {
    private final FirestoreRepository firestoreRepository;
    private final FirebaseAuthService firebaseAuthService;
    private final AdminService adminService;

    public InstitutionService(FirestoreRepository firestoreRepository, FirebaseAuthService firebaseAuthService, AdminService adminService){
        this.firestoreRepository = firestoreRepository;
        this.firebaseAuthService = firebaseAuthService;
        this.adminService = adminService;

    };


    public void createInstitution(String adminUid,String institutionName, String type, String contactEmail, String location, String address) throws ExecutionException, InterruptedException {
        Institution institution = new Institution(contactEmail, type, institutionName, location, address);

        String institutionDocId = firestoreRepository.createDocumentFromObject(FirestoreCollections.INSTITUTIONS, institution);

        firestoreRepository.updateDocumentFromField(FirestoreCollections.INSTITUTION_ADMINS,adminUid,"institution_id",institutionDocId);
    }

    public String getInstitutionIdFromAuthToken(String authToken) throws ExecutionException, InterruptedException {
        String adminUid = firebaseAuthService.extractAndVerifyIdToken(authToken);

        return adminService.getInstitutionIdFromAdminUid(adminUid);
    }

    public boolean getInstitutionConfigurationStatus(String uid) throws ExecutionException, InterruptedException {
        DocumentSnapshot adminDocument = firestoreRepository.queryByDocumentId(FirestoreCollections.INSTITUTION_ADMINS,uid);

        InstitutionAdmin admin = adminDocument.toObject(InstitutionAdmin.class);

        return !admin.getInstitutionId().equals(ValidationConstants.ADMIN_INSTITUTION_REGISTERED);
    }
}
