package com.learn.userprofile.service;

import com.learn.userprofile.exceptions.UserExistsException;
import com.learn.userprofile.exceptions.UserNotFoundException;
import com.learn.userprofile.model.User;
import com.learn.userprofile.model.UserCredentials;
import com.learn.userprofile.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserProfileRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    User userOne;
    UserCredentials credentials;
    @BeforeEach
    public void setUp(){
      userOne = new User(1, "TestName", "test@gmail.com", "testpassword");
         credentials = new UserCredentials("test@gmail.com", "testpassword");
    }

    @Test
    public void givenUserDetailsWhenUserDoesnotExistsThenRetunSavedUser() throws UserExistsException {

        //configure the behavoiur of Mock object
       when(repository.existsByEmail(userOne.getEmail())).thenReturn(false);
       when(repository.save(any(User.class))).thenReturn(userOne);

        //actual test cases
        User user = service.registerUser(userOne);
       assertAll(
               ()->{assertNotNull(user);},
               ()->{assertTrue(user.getEmail().equals("test@gmail.com"));},
               ()->{assertTrue(user.getName().equals("TestName"));}
       );

       //verify the Mock calls made by Service or Not
        verify(repository,atLeastOnce()).existsByEmail(anyString());
        verify(repository,times(1)).save(any(User.class));
        verifyNoMoreInteractions(repository);


    }

    @Test
    public void givenUserDetailsWhenUserExistsThenThrowException () throws UserExistsException {
        when(repository.existsByEmail(userOne.getEmail())).thenReturn(true);

        assertThrows(UserExistsException.class,()->service.registerUser(userOne));

        verify(repository,atLeastOnce()).existsByEmail(anyString());
        verifyNoMoreInteractions(repository);

    }

    @Test
    public void givenUserCredentialsWhenValidThenReturnTrue() throws UserNotFoundException {

        when(repository.getUserByEmail(credentials.getEmail())).thenReturn(Optional.of(userOne));


        assertTrue(service.authenticateUser(credentials));

//        verify(repository,atLeastOnce()).getUserByEmail(anyString());
        verify(repository).getUserByEmail(anyString());

    }

    @Test
    public void givenUserCredentialsWhenDoesNotExistThenThrowException(){

        when(repository.getUserByEmail(credentials.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,()->service.authenticateUser(credentials));
    }

}