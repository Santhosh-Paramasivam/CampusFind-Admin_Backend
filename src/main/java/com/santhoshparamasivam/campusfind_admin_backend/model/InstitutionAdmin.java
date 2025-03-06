package com.santhoshparamasivam.campusfind_admin_backend.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

public class InstitutionAdmin {
    @DocumentId
    private String id;

    @PropertyName("email_id")
    private String emailId;
    @PropertyName("institution_id")
    private String institutionId;
    @PropertyName("username")
    private String username;


    public InstitutionAdmin(){}

    public InstitutionAdmin(String id, String emailId, String institutionId, String username){
        this.id = id;
        this.emailId = emailId;
        this.institutionId = institutionId;
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    @PropertyName("email_id")
    public String getEmailId() {
        return emailId;
    }

    @PropertyName("email_id")
    public void setEmailId (String emailId) {
        this.emailId = emailId;
    }


    @PropertyName("institution_id")
    public String getInstitutionId() {
        return institutionId;
    }

    @PropertyName("institution_id")
    public void setInstitutionId (String institutionId) {
        this.institutionId = institutionId;
    }

    @PropertyName("username")
    public String getUsername() {
        return username;
    }

    @PropertyName("username")
    public void setUsername (String username) {
        this.username = username;
    }
}
