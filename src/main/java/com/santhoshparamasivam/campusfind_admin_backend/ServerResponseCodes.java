package com.santhoshparamasivam.campusfind_admin_backend;

import java.util.HashMap;

public class ServerResponseCodes {
    public static final String USER_REGISTERED = "user_register";
    public static final HashMap<String, String> RESPONSE_MAP = new HashMap<>();

    static {
        RESPONSE_MAP.put(USER_REGISTERED,"The user is registered successfully in Firebase Authentication");
    }
}
