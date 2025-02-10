package com.santhoshparamasivam.campusfind_admin_backend;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.protobuf.Api;
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

    public FirestoreRepository(Firestore firestore)
    {
        this.firestore = firestore;

    }

    public List<QueryDocumentSnapshot> getDocumentsWhere(String collectionId, String field, String value) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = firestore.collection(collectionId).whereEqualTo(field,value).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        return documents;
    }

    public void updateDocumentFromHashMap(String collectionName, String documentId, Map<String, Object> docData) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = firestore.collection(collectionName).document(documentId).set(docData);
        logger.info("Update time : " + future.get().getUpdateTime());
    }

    public void createDocumentFromHashMap(String collectionName, Map<String, Object> docData) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection(collectionName).add(docData);
        logger.info("Add time : " + future.get().get().get().getUpdateTime());
    }


    public DocumentReference queryFirstDocument(String collection) throws ExecutionException, InterruptedException {
        CollectionReference institution_buildings = firestore.collection(collection);

        DocumentReference document = institution_buildings.listDocuments().iterator().next();

        return document;
    }

    public CollectionReference querySubcollectionIteratively(String collectionPath) throws ExecutionException, InterruptedException {
        String[] collectionIdList = collectionPath.split("/");
        DocumentReference documentReference = null;
        CollectionReference collectionReference = null;
        boolean first = true;

        for(String collectionId : collectionIdList)
        {
            if(first)
            {
                documentReference = queryFirstDocument(collectionId);
                first = false;
            }
            else
            {

                collectionReference = documentReference.collection(collectionId);
                documentReference = collectionReference.listDocuments().iterator().next();
            }

            logger.info(collectionId);
            logger.info(documentReference.getId());
        }

        return collectionReference;
    }

    public DocumentSnapshot querySubcollectionDocument(String collectionPath) throws ExecutionException, InterruptedException {
        CollectionReference deepestReference = querySubcollectionIteratively(collectionPath);


        DocumentReference document = deepestReference.listDocuments().iterator().next();

        ApiFuture<DocumentSnapshot> future = document.get();

        logger.info("deepestReference document" + future.get().toString());

        return future.get();
    }


    public void createDocumentFromObjectAndDocumentId(String collectionName,String documentId,Object object) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = firestore.collection(collectionName).document(documentId).set(object);
        logger.info("Add time : " + future.get());
    }

    public void createDocumentFromObject(String collectionName,Object object) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = firestore.collection(collectionName).add(object);
        logger.info("Add time : " + future.get());
    }

    public void updateDocumentFromField(String collectionName,String documentId, String field, String value) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = firestore.collection(collectionName).document(documentId).update(field, value);
        logger.info("Add time : " + future.get());
    }
}
