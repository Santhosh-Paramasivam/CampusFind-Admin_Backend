package com.santhoshparamasivam.campusfind_admin_backend.Services;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.santhoshparamasivam.campusfind_admin_backend.FirestoreCollections;
import com.santhoshparamasivam.campusfind_admin_backend.FirestoreRepository;
import com.santhoshparamasivam.campusfind_admin_backend.Models.InstitutionAdmin;
import com.santhoshparamasivam.campusfind_admin_backend.Models.InstitutionMemberSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    private final Logger logger = LoggerFactory.getLogger(FirestoreService.class);
    private final FirestoreRepository repository;

    public FirestoreService(FirestoreRepository repository) {
        this.repository = repository;
    }

    public void getUser(String name) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documents = this.repository.getDocumentsWhere(FirestoreCollections.INSTITUTION_MEMBERS,"name",name);

        DocumentSnapshot user = documents.get(0);

        logger.info(user.toString());
    }

    public void updateData() throws ExecutionException, InterruptedException {
        HashMap<String, Object> documentData = new HashMap<>();
        documentData.put("name","Santhosh");
        documentData.put("age",18);
        repository.updateDocumentFromHashMap("admin_trial", "docId",documentData);
    }

    public void addInstitutionAdmins(String documentId, String institutionId, String emailId, String username) throws ExecutionException, InterruptedException {
        InstitutionAdmin institutionAdmin = new InstitutionAdmin(documentId, emailId, institutionId, username);

        repository.createDocumentFromObjectAndDocumentId(FirestoreCollections.INSTITUTION_ADMINS, documentId, institutionAdmin);
    }

    public void updateInstitutionAdmins(String documentId, String field, String value) throws ExecutionException, InterruptedException {
        repository.updateDocumentFromField(FirestoreCollections.INSTITUTION_ADMINS, documentId, "institutionId", "");
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

        this.repository.createDocumentFromObject(FirestoreCollections.INSTITUTION_MEMBER_SCHEMAS, institutionMemberSchema);
    }


}
