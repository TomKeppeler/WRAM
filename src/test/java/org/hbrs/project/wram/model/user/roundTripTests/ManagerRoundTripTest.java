package org.hbrs.project.wram.model.user.roundTripTests;

import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ManagerRoundTripTest {

    User user = new User();
    User userInDB;
    Manager manager = new Manager();
    @Autowired
    private UserService userService;
    @Autowired
    private ManagerService managerService;

    @BeforeEach
    void setUp() {
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
        assertEquals(manager.hashCode(), manager.hashCode());
        //delete
        managerService.deleteManagerById(manager2.getId());
        //userService.deleteUserById(userInDB.getId()); -> passiert automatisch
    }


}