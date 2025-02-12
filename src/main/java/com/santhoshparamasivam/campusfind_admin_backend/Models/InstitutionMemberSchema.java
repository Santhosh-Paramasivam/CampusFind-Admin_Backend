package com.santhoshparamasivam.campusfind_admin_backend.Models;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.Map;

public class InstitutionMemberSchema {
    @DocumentId
    private String id;

    @PropertyName("institution_id")
    private String institutionId;
    @PropertyName("schema")
    private Map<String,Map<String,String>> schema;

    public InstitutionMemberSchema(){}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @PropertyName("institution_id")
    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    @PropertyName("institution_id")
    public String getInstitutionId() {
        return institutionId;
    }

    @PropertyName("schema")
    public void setSchema(Map<String, Map<String, String>> schema) {
        this.schema = schema;
    }

    @PropertyName("schema")
    public Map<String, Map<String, String>> getSchema() {
        return schema;
    }

    @Override
    public String toString()
    {
        return "id : " + id + " institution_id : " + institutionId + " schema : " + schema;
    }
}
