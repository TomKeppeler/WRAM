package org.hbrs.project.wram.model.user.roundTripTests;

import org.hbrs.project.wram.control.entwickler.EntwicklerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EntwicklerRoundTripTest {

    User user = new User();
    User userInDB;
    Entwickler entwickler = new Entwickler();
    @Autowired
    private UserService userService;
    @Autowired
    private EntwicklerService entwicklerService;

    @BeforeEach
    void setUp() {
        user = User.builder().email("max@mustermann.de").password("testpw12").username("maxMuster").build();
        userService.doCreateUser(user);
        userInDB = userService.findUserByUsernameAndPassword("maxMuster", "testpw12");
        entwickler = Entwickler.builder().firstname("hans").name("Hans").user(user).build();
    }


    @Test
    void ManagerRoundTrip() {
        //create
        entwicklerService.doCreatEntwickler(entwickler);
        //read
        Entwickler entwickler2 = entwicklerService.findEntwicklerByUserId(userInDB.getId());
        //compare
        assertEquals(entwickler, entwickler2);
        assertEquals(entwickler.hashCode(), entwickler.hashCode());
        //delete
        entwicklerService.deleteEntwicklerById(entwickler2.getId());
        //userService.deleteUserById(userInDB.getId()); -> passiert automatisch
    }


}