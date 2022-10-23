package org.hbrs.project.wram.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<User,UUID> {

    @Query("SELECT User from User u where u.username=:username and u.password=:password")
    User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("SELECT (count(u.id) > 0) from User u where u.email=:email")
    boolean isEmailInUse(@Param("email") String email);
}
