package com.santhoshparamasivam.campusfind_admin_backend.Services;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.santhoshparamasivam.campusfind_admin_backend.FirestoreCollections;
import com.santhoshparamasivam.campusfind_admin_backend.FirestoreRepository;
import com.santhoshparamasivam.campusfind_admin_backend.Models.InstitutionMemberSchema;
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

    public void addMemberSchema() throws ExecutionException, InterruptedException {
        InstitutionMemberSchema institutionMemberSchema = new InstitutionMemberSchema();
        Map<String, Map<String,String>> schema = new HashMap<>();

        Map<String, String> professor = new HashMap<>();
        professor.put("Faculty Id","string");
        Map<String, String> student = new HashMap<>();
        student.put("Register Id","string");

        schema.put("Professor",professor);
        schema.put("Student",student);

        institutionMemberSchema.setInstitutionId("");
        institutionMemberSchema.setSchema(schema);

        this.firestoreRepository.createDocumentFromObject(FirestoreCollections.INSTITUTION_MEMBER_SCHEMAS, institutionMemberSchema);
    }
}