package com.learn.userprofile.service;

import com.learn.userprofile.exceptions.UserExistsException;
import com.learn.userprofile.exceptions.UserNotFoundException;
import com.learn.userprofile.model.User;
import com.learn.userprofile.model.UserCredentials;

public interface UserService {
    User registerUser(User newUser) throws UserExistsException;

    boolean authenticateUser(UserCredentials credentials) throws UserNotFoundException;
}
