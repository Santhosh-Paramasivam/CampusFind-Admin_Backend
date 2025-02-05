package com.santhoshparamasivam.campusfind_admin_backend;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.google.cloud.firestore.Firestore;

import java.io.IOException;

@Configuration
public class FirestoreConfig {

    private static Firestore firestore;

    @Bean
    public Firestore firestore() throws IOException {
        if (firestore == null) {
            synchronized (FirestoreConfig.class) {
                if (firestore == null) {
                    GoogleCredentials credentials = GoogleCredentials.fromStream(
                            new ClassPathResource("service_key.json").getInputStream() // Correct path
                    );

                    FirebaseOptions options = FirebaseOptions.builder()
                            .setCredentials(credentials)
                            .build();

                    FirebaseApp.initializeApp(options);

                    firestore = FirestoreClient.getFirestore();
                }
            }
        }
        return firestore;
    }
}
