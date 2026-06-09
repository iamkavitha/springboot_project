package com.learn.userprofile.repository;

import com.learn.userprofile.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<User,Integer> {

          boolean existsByEmail (String email);

//          Optional<User> findByEmail(String email);

          @Query("select u from User u where u.email= ?1")
          Optional<User> getUserByEmail (String email);
}
