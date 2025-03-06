package com.santhoshparamasivam.campusfind_admin_backend;

import java.util.HashMap;

public class ServerErrorCodes {
    public static final String INTERNAL_SERVER_ERROR = "internal_server_error";
    public static final String FIREBASE_AUTH_ERROR = "firebase_auth_error";
    public static final String FIRESTORE_ERROR = "firestore_error";

    public static final HashMap<String, String> ERROR_MAP = new HashMap<>();

    static {
        ERROR_MAP.put(INTERNAL_SERVER_ERROR,"A server-side error has occured");
    }
}
