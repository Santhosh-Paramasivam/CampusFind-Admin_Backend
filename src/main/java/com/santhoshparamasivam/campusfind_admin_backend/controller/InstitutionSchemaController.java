package com.santhoshparamasivam.campusfind_admin_backend.controller;

import com.santhoshparamasivam.campusfind_admin_backend.model.InstitutionMemberSchema;
import com.santhoshparamasivam.campusfind_admin_backend.service.InstitutionSchemaService;
import com.santhoshparamasivam.campusfind_admin_backend.service.InstitutionService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class InstitutionSchemaController {
    private final InstitutionService institutionService;
    private final InstitutionSchemaService institutionSchemaService;
    InstitutionSchemaController(InstitutionService institutionService, InstitutionSchemaService institutionSchemaService){
        this.institutionService = institutionService;
        this.institutionSchemaService = institutionSchemaService;
    }
    @PostMapping("/create_schema")
    public void createInstitutionSchema(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody Map<String, Map<String,String>> schema) throws ExecutionException, InterruptedException {
        String institutionId = institutionService.getInstitutionIdFromAuthToken(authHeader);
        InstitutionMemberSchema institutionMemberSchema = new InstitutionMemberSchema(schema, institutionId);

        institutionSchemaService.createMemberSchema(institutionMemberSchema);
    }

    @PostMapping("/update_schema")
    public void updateInstitutionSchema(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody Map<String, Map<String,String>> schema) throws ExecutionException, InterruptedException {
        String institutionId = institutionService.getInstitutionIdFromAuthToken(authHeader);
        InstitutionMemberSchema institutionMemberSchema = new InstitutionMemberSchema(schema, institutionId);

        institutionSchemaService.updateMemberSchema(institutionMemberSchema);
    }
}
