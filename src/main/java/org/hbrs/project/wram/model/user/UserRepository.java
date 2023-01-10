/**
 * @outhor Tom & Sophia
 * @vision 1.0
 * @Zuletzt bearbeiret: 14.11.22 by Salah
 */

package org.hbrs.project.wram.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.UUID;

/** JPA Repository für User CRUD eines */

@Component
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select user from User user where user.username=:username and user.password=:password")
    User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("select user from User user where user.password=:password")
    User findUserByPassword(@Param("password") String password);

    @Query("select user from User user where user.username=:username")
    User findUserByUsername(@Param("username") String username);

    @Query("SELECT (count(u.id) > 0)    from User u where u.email=:email")
    boolean isEmailInUse(@Param("email") String email);


    @Query("SELECT (count(u.id) > 0) from User u where u.username=:username")
    boolean isUsernameInUse(@Param("username") String username);

    @Query("select user from User user where user.id=:id")
    User findUserById(@Param("id") UUID id);

    @Query("select u from User u WHERE u.verificationCode = ?1")
    User findByVerificationCode(String code);

    //todo:Insert in jpa


}
