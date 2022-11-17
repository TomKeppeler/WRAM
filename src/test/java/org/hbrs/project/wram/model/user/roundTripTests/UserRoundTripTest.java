package org.hbrs.project.wram.model.user.roundTripTests;

import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRoundTripTest {

    @Autowired
    private UserService userService;

    User user = new User();

    @BeforeEach
    void setUp(){
        user = User.builder().email("max@mustermann.de").password("testpw12").username("maxMuster").build();
    }

    @Test
    void UserRoundTrip() {
        //create
        userService.doCreateUser(user);
        //read
        User user2 = userService.findUserByUsernameAndPassword("maxMuster","testpw12");
        //compare
        assertEquals(user, user2);
        assertEquals(user.hashCode(), user.hashCode());
        //delete
        userService.deleteUserById(user2.getId());
    }


}