package com.santhoshparamasivam.campusfind_admin_backend;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.stereotype.Service;

@Service
public class FirebaseAuthService {

    private FirebaseAuth firebaseAuth;
    FirebaseAuthService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }
    public static String verifyIdToken(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            System.out.println("✅ Verified user UID: " + uid);
            return uid;
        } catch (FirebaseAuthException e) {
            System.err.println("❌ Invalid ID token: " + e.getMessage());
            return null;
        }
    }


}
