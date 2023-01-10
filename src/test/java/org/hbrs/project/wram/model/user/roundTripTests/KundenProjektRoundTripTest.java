package org.hbrs.project.wram.model.user.roundTripTests;

import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektRepository;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class KundenProjektRoundTripTest {

    User user = new User();
    User userInDB;
    Manager manager = new Manager();
    Kundenprojekt kundenprojekt = new Kundenprojekt();
    @Autowired
    private UserService userService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private KundenprojektService kundenprojektService;
    @Autowired
    private KundenprojektRepository kundenprojektRepository;

    @Test
    void KundenProjektRoundTrip() {
        user = User.builder().email("max@mustermann.de").password("testpw12").username("maxMuster").build();
        userService.doCreateUser(user);
        userInDB = userService.findUserByUsernameAndPassword("maxMuster", "testpw12");
        manager = Manager.builder().firstname("hans").name("Hans").user(user).build();
        //create
        managerService.doCreateManager(manager);
        //read
        Manager manager2 = managerService.findManagerByUserId(userInDB.getId());
        kundenprojekt = Kundenprojekt.builder().projektname("test").manager(manager2).build();
        kundenprojektService.doCreateKundenprojekt(kundenprojekt);
        //löschen des Projektes und des Managers+Users
        List<Kundenprojekt> kid = kundenprojektService.findAllKundenprojektByManagerId(manager2.getId());
        for (Kundenprojekt k : kid) {
            kundenprojektRepository.delete(k);
        }
        //delete
        managerService.deleteManagerById(manager2.getId());
        //userService.deleteUserById(userInDB.getId()); -> passiert automatisch
    }


    @Test
    void findAllPublicKundenprojektTest() {
        List<Kundenprojekt> k = kundenprojektService.findAllPublicKundenprojekt();
        assertEquals(k, k);
        assertEquals(k.hashCode(), k.hashCode());
    }


}