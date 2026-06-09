package com.learn.userprofile.service;

import com.learn.userprofile.exceptions.UserExistsException;
import com.learn.userprofile.exceptions.UserNotFoundException;
import com.learn.userprofile.model.User;
import com.learn.userprofile.model.UserCredentials;
import com.learn.userprofile.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserProfileRepository repository;

    @Autowired
    public UserServiceImpl(UserProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public User registerUser(User newUser) throws UserExistsException {
         if(repository.existsByEmail(newUser.getEmail())){
             throw new UserExistsException("User with the email already exists");
         }

        return repository.save(newUser);
    }

    @Override
    public boolean authenticateUser(UserCredentials credentials) throws UserNotFoundException {
        Optional<User> userByEmail = repository.getUserByEmail(credentials.getEmail());

        if (userByEmail.isEmpty()){
            throw  new UserNotFoundException("User not Found");
        }

        User user = userByEmail.get();
        if(user.getPassword().equals(credentials.getPassword())){
            return true;
        }
        else{
            throw new RuntimeException("Credentials mismatch");
        }


    }
}
