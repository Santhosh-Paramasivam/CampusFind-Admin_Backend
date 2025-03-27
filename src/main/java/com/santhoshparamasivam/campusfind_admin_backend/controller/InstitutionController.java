package com.santhoshparamasivam.campusfind_admin_backend.controller;

import com.santhoshparamasivam.campusfind_admin_backend.service.FirebaseAuthService;
import com.santhoshparamasivam.campusfind_admin_backend.service.InstitutionService;
import com.santhoshparamasivam.campusfind_admin_backend.utils.ServerResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RequestMapping("/institution")
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class InstitutionController {
    InstitutionService institutionService;
    FirebaseAuthService firebaseAuthService;

    InstitutionController(InstitutionService institutionService, FirebaseAuthService firebaseAuthService) {
       this.institutionService = institutionService;
       this.firebaseAuthService = firebaseAuthService;
    }

    @GetMapping("/configuration_status")
    public ResponseEntity<Map<String, Object>> adminInstitutionConfigured(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) throws ExecutionException, InterruptedException {
        boolean configuration = institutionService.getInstitutionConfigurationStatus(firebaseAuthService.extractAndVerifyIdToken(authToken));

        Map<String,Object> requestBody = new HashMap<>();
        requestBody.put("institution_configuration_status",configuration);
        return ServerResponse.respond(HttpStatus.ACCEPTED, requestBody);
    }
}
