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
        List<QueryDocumentSnapshot> documents = this.repository.getDocumentsWhere("institution_members","name",name);

        DocumentSnapshot user = documents.get(0);

        logger.info(user.toString());
    }

    void updateData() throws ExecutionException, InterruptedException {
        HashMap<String, Object> documentData = new HashMap<>();
        documentData.put("name","Santhosh");
        documentData.put("age",18);
        repository.updateDocumentFromHashMap("admin_trial", "docId",documentData);
    }

    void addInstitutionAdmins() throws ExecutionException, InterruptedException {
        InstitutionAdmin institutionAdmin = new InstitutionAdmin();
        institutionAdmin.setId("docId");
        institutionAdmin.setInstitutionId("Santhosh");
        institutionAdmin.setEmailId("Santhosh");
        institutionAdmin.setUsername("Santhosh");

        repository.createDocumentFromObject("institution_admins", "docId", institutionAdmin);
    }


}
