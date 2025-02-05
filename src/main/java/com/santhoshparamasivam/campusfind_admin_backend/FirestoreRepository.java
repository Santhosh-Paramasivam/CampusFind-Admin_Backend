package com.santhoshparamasivam.campusfind_admin_backend;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Repository
public class FirestoreRepository {

    private final Logger logger = LoggerFactory.getLogger(FirestoreRepository.class);
    private final Firestore firestore;

    FirestoreRepository(Firestore firestore)
    {
        this.firestore = firestore;

    }

    List<QueryDocumentSnapshot> getDocumentsWhere(String collectionId, String field, String value) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = firestore.collection(collectionId).whereEqualTo(field,value).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        return documents;
    }

    void addData(String collectionName, String documentId, Map<String, Object> docData) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = firestore.collection(collectionName).document(documentId).set(docData);
        logger.info("Update time : " + future.get().getUpdateTime());
    }



}
