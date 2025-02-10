package com.santhoshparamasivam.campusfind_admin_backend.Models;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import java.util.UUID;

public class Institution {
    @DocumentId
    private String id;

    @PropertyName("type")
    private String type;
    @PropertyName("contact_email")
    private String contactEmail;
    @PropertyName("institution_id")
    private String institutionId;

    @PropertyName("institution_name")
    private String institutionName;
    @PropertyName("location")
    private String location;
    @PropertyName("address")
    private String address;

    public Institution(){}

    public Institution(String contactEmail, String type, String institutionName, String location, String address){
        this.contactEmail = contactEmail;
        this.type = type;
        this.institutionId = UUID.randomUUID().toString();
        this.institutionName = institutionName;
        this.location = location;
        this.address = address;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("institution_id")
    public String getInstitutionId() {
        return institutionId;
    }

    @PropertyName("institution_id")
    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    @PropertyName("address")
    public String getAddress() {
        return address;
    }

    @PropertyName("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @PropertyName("institution_name")
    public String getInstitutionName() {
        return institutionName;
    }

    @PropertyName("institution_name")
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @PropertyName("contact_email")
    public String getContactEmail() {
        return contactEmail;
    }

    @PropertyName("contact_email")
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @PropertyName("location")
    public String getLocation() {
        return location;
    }

    @PropertyName("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @PropertyName("type")
    public String getType() {
        return type;
    }

    @PropertyName("type")
    public void setType(String type) {
        this.type = type;
    }
}
