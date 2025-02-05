package com.santhoshparamasivam.campusfind_admin_backend;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    void addData() throws ExecutionException, InterruptedException {
        HashMap<String, Object> documentData = new HashMap<String, Object>();
        documentData.put("name","Santhosh");
        documentData.put("age",18);
        repository.addData("admin_trial", "3yhNFjL8dIfeZJHxpQBn",documentData);
    }

}
