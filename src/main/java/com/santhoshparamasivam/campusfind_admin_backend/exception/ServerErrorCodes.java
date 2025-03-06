package com.santhoshparamasivam.campusfind_admin_backend.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerErrorCodes {
    public static final String AUTH_TOKEN_MISSING = "auth-token-missing";
    public static final String INVALID_JWT_TOKEN = "invalid_jwt_token";
    public static final String FIREBASE_AUTH_ERROR = "firebase_auth_error";
    public static final Map<String, String> ERROR_MAP;

    static {
        ERROR_MAP = Map.of(
                AUTH_TOKEN_MISSING, "JWT token for authentication not found in request header",
                INVALID_JWT_TOKEN, "The provided Firebase JWT Auth token is malformed or expired"
        );
    }
}
