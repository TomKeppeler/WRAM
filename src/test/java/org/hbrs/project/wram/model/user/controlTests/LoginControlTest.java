package org.hbrs.project.wram.model.user.controlTests;

import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginControlTest {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginControl loginControl;

    User user = new User();


    @Test
    void testLoginAuthentication() throws Exception {
        assertFalse(loginControl.authenticateUser("maxMuster", "Test12345"));
    }


}