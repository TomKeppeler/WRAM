package org.hbrs.project.wram.model.user;

import org.hbrs.project.wram.control.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private UserService userService;

    @Test
    void postgresAvailableTest() {
        assertDoesNotThrow(this.userService::findAllUsers, "Error");
    }


}