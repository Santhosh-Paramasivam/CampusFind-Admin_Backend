package com.santhoshparamasivam.campusfind_admin_backend.service;

import com.santhoshparamasivam.campusfind_admin_backend.utils.FirestoreCollections;
import com.santhoshparamasivam.campusfind_admin_backend.repository.FirestoreRepository;
import com.santhoshparamasivam.campusfind_admin_backend.model.Institution;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class InstitutionService {
    private final FirestoreRepository firestoreRepository;

    public InstitutionService(FirestoreRepository firestoreRepository){
        this.firestoreRepository = firestoreRepository;
    };


    public void createInstitution(String adminUid,String institutionName, String type, String contactEmail, String location, String address) throws ExecutionException, InterruptedException {
        Institution institution = new Institution(contactEmail, type, institutionName, location, address);

        String institutionDocId = firestoreRepository.createDocumentFromObject(FirestoreCollections.INSTITUTIONS, institution);

        firestoreRepository.updateDocumentFromField(FirestoreCollections.INSTITUTION_ADMINS,adminUid,"institution_id",institutionDocId);
    }
}
