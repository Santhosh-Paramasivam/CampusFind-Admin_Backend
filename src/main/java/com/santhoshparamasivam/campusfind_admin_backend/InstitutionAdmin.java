package com.santhoshparamasivam.campusfind_admin_backend;

import com.google.cloud.firestore.annotation.DocumentId;

public class InstitutionAdmin {
    @DocumentId
    private String id;
    private String emailId;
    private String institutionId;
    private String username;

    public InstitutionAdmin(){}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId (String emailId) {
        this.emailId = emailId;
    }


    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId (String institutionId) {
        this.institutionId = institutionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }
}
