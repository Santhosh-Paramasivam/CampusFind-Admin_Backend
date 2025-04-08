package com.santhoshparamasivam.campusfind_admin_backend.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.santhoshparamasivam.campusfind_admin_backend.exception.ServerException;
import com.santhoshparamasivam.campusfind_admin_backend.service.FirebaseAuthService;
import com.santhoshparamasivam.campusfind_admin_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final FirebaseAuthService firebaseAuthService;
    UserController(UserService userService, FirebaseAuthService firebaseAuthService) {
        this.userService = userService;
        this.firebaseAuthService = firebaseAuthService;
    }

    @PostMapping("/add")
    void _addUser(@RequestBody HashMap<String, Object> body) throws ExecutionException, InterruptedException, FirebaseAuthException {
        if(!body.containsKey("email_id")) {
            throw new ServerException("missing-field","email_id field missing", HttpStatus.BAD_REQUEST);
        } else if(!body.containsKey("password")) {
            throw new ServerException("missing-field","password field missing", HttpStatus.BAD_REQUEST);
        } else if(!body.containsKey("in_room")) {
            throw new ServerException("missing-field","in_room field missing", HttpStatus.BAD_REQUEST);
        } else if(!body.containsKey("institution_id")) {
            throw new ServerException("missing-field","institution_id field missing", HttpStatus.BAD_REQUEST);
        } else if(!body.containsKey("last_location_entry")) {
            throw new ServerException("missing-field","last_location_entry field missing", HttpStatus.BAD_REQUEST);
        } else if(!body.containsKey("rfid_location")) {
            throw new ServerException("missing-field","rfid_location field missing", HttpStatus.BAD_REQUEST);
        } else if(!body.containsKey("rfid_uid")) {
            throw new ServerException("missing-field","rfid_uid field missing", HttpStatus.BAD_REQUEST);
        } else if(!body.containsKey("status")) {
            throw new ServerException("missing-field","status field missing", HttpStatus.BAD_REQUEST);
        } else if(!body.containsKey("user_role")) {
            throw new ServerException("missing-field","user_role field missing", HttpStatus.BAD_REQUEST);
        }

        userService.addUser(body);
    }
}
