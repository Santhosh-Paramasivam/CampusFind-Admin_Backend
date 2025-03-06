package com.santhoshparamasivam.campusfind_admin_backend;


import org.springframework.http.HttpStatus;

public class ServerException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;

    public ServerException(String errorCode, String message, HttpStatus httpStatus)
    {
         super(message);
         this.errorCode = errorCode;
         this.status = httpStatus;
    }

    public ServerException(String errorCode, String message, int statusCode)
    {
        super(message);
        this.errorCode = errorCode;
        this.status = HttpStatus.valueOf(statusCode);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode()
    {
        return this.errorCode;
    }
}
