package com.santhoshparamasivam.campusfind_admin_backend.service;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.santhoshparamasivam.campusfind_admin_backend.utils.FirestoreCollections;
import com.santhoshparamasivam.campusfind_admin_backend.repository.FirestoreRepository;
import com.santhoshparamasivam.campusfind_admin_backend.model.InstitutionMemberSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class InstitutionSchemaService {

    FirestoreRepository firestoreRepository;
    AdminService adminService;
    Logger logger = LoggerFactory.getLogger(InstitutionSchemaService.class);
    public InstitutionSchemaService(FirestoreRepository firestoreRepository, AdminService adminService){
        this.firestoreRepository = firestoreRepository;
        this.adminService = adminService;
    }
    public InstitutionMemberSchema getMemberSchemaFromAdminUid(String uid) throws ExecutionException, InterruptedException {
        String institutionId = adminService.getInstitutionIdFromAdminUid(uid);

        List<QueryDocumentSnapshot> queryDocumentSnapshotList = firestoreRepository.getDocumentsWhere(FirestoreCollections.INSTITUTION_MEMBER_SCHEMAS, "institution_id", "12345");

        QueryDocumentSnapshot queryDocumentSnapshot = queryDocumentSnapshotList.get(0);


        InstitutionMemberSchema institutionMemberSchema =  queryDocumentSnapshot.toObject(InstitutionMemberSchema.class);
        logger.info(institutionMemberSchema.toString());

        return institutionMemberSchema;
    }

    public void createMemberSchema(InstitutionMemberSchema institutionMemberSchema) throws ExecutionException, InterruptedException {
        this.firestoreRepository.createDocumentFromObject(FirestoreCollections.INSTITUTION_MEMBER_SCHEMAS, institutionMemberSchema);
    }

    public void updateMemberSchema(InstitutionMemberSchema institutionMemberSchema) throws ExecutionException, InterruptedException {
        QueryDocumentSnapshot queryDocumentSnapshot = firestoreRepository.getDocumentWhere(FirestoreCollections.INSTITUTION_MEMBER_SCHEMAS, "institution_id", institutionMemberSchema.getInstitutionId());

        this.firestoreRepository.createDocumentFromObjectAndDocumentId(FirestoreCollections.INSTITUTION_MEMBER_SCHEMAS, queryDocumentSnapshot.getId(),institutionMemberSchema);
    }
}