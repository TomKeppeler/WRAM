package org.hbrs.project.wram.model.user.roundTripTests;

import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.user.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ManagerRoundTripTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;

    User user = new User();
    User userInDB;
    Manager manager = new Manager();

    @BeforeEach
    void setUp(){
        user = User.builder().email("max@mustermann.de").password("testpw12").username("maxMuster").build();
        userService.doCreateUser(user);
        userInDB = userService.findUserByUsernameAndPassword("maxMuster", "testpw12");
        manager = Manager.builder().firstname("hans").name("Hans").user(user).build();
    }


    @Test
    void ManagerRoundTrip() {
        //create
        managerService.doCreateManager(manager);
        //read
        Manager manager2 = managerService.findManagerByUserId(userInDB.getId());
        //compare
        assertEquals(manager, manager2);
        //delete
        managerService.deleteManagerById(manager2.getId());
        //userService.deleteUserById(userInDB.getId()); -> passiert automatisch
    }


}