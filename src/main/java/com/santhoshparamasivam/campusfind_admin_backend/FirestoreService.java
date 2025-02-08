package com.santhoshparamasivam.campusfind_admin_backend;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    private final Logger logger = LoggerFactory.getLogger(FirestoreService.class);
    private final FirestoreRepository repository;

    FirestoreService(FirestoreRepository repository) {
        this.repository = repository;
    }

    void getUser(String name) throws ExecutionException, InterruptedException {
        List<QueryDocumentSnapshot> documents = this.repository.getDocumentsWhere(FirestoreCollections.INSTITUTION_MEMBERS,"name",name);

        DocumentSnapshot user = documents.get(0);

        logger.info(user.toString());
    }

    void updateData() throws ExecutionException, InterruptedException {
        HashMap<String, Object> documentData = new HashMap<>();
        documentData.put("name","Santhosh");
        documentData.put("age",18);
        repository.updateDocumentFromHashMap("admin_trial", "docId",documentData);
    }

    void addInstitutionAdmins(String documentId, String institutionId, String emailId, String username) throws ExecutionException, InterruptedException {
        InstitutionAdmin institutionAdmin = new InstitutionAdmin(documentId, emailId, institutionId, username);

        repository.createDocumentFromObject(FirestoreCollections.INSTITUTION_ADMINS, documentId, institutionAdmin);
    }

    void updateInstitutionAdmins(String documentId, String institutionId, String emailId, String username) throws ExecutionException, InterruptedException {
        InstitutionAdmin institutionAdmin = new InstitutionAdmin(documentId, emailId, institutionId, username);

        repository.updateDocumentFromField(FirestoreCollections.INSTITUTION_ADMINS, documentId, "institutionId", "12345");
    }
}
