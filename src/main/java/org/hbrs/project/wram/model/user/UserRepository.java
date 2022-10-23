package org.hbrs.project.wram.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
public interface UserRepository extends JpaRepository<User,UUID> {

    @Query("select user from User user where user.username=:username and user.password=:password")
    User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("SELECT (count(u.id) > 0) from User u where u.email=:email")
    boolean isEmailInUse(@Param("email") String email);
}
