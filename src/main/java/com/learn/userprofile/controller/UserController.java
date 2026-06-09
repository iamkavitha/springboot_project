package com.learn.userprofile.controller;

import com.learn.userprofile.exceptions.UserExistsException;
import com.learn.userprofile.exceptions.UserNotFoundException;
import com.learn.userprofile.model.User;
import com.learn.userprofile.model.UserCredentials;
import com.learn.userprofile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User newUser) throws UserExistsException {
        return new ResponseEntity<>(service.registerUser(newUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserCredentials credentials) throws UserNotFoundException {
        boolean valid = service.authenticateUser(credentials);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
