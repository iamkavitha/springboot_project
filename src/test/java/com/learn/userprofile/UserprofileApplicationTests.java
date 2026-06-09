package com.learn.userprofile;

import com.learn.userprofile.model.User;
import com.learn.userprofile.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
//@SpringBootTest
class UserprofileApplicationTests {

	@Autowired
	private UserProfileRepository repository;


	@BeforeEach
	public void setup(){
		User user = new User(0, "Charlie", "charlie@gmail.com", "pass123");
		repository.save(user);
	}

	@Test
	public void givenUserDoesnotExistsInDBThenReturnFalse(){
		assertFalse(repository.existsByEmail("sagar@gmail.com"),"User does not exist in database");
	}

	@Test
	public void givenUserExistsInDBThenReturnTrue(){
		assertTrue(repository.existsByEmail("charlie@gmail.com"),"User does not exist in database");
	}

	@Test
	public void givenUserEmailWhenExistsReturnOptionalWithUser(){
		Optional<User> optionalUser = repository.getUserByEmail("charlie@gmail.com");
		assertTrue(optionalUser.isPresent());

		User user = optionalUser.get();
		assertEquals("Charlie",user.getName());

	}



}
