package org.hbrs.project.wram.model.user.roundTripTests;

import org.hbrs.project.wram.control.kundenprojekt.KundenprojektService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektRepository;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class KundenProjektRoundTripTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private KundenprojektService kundenprojektService;

    @Autowired
    private KundenprojektRepository kundenprojektRepository;

    User user = new User();
    User userInDB;
    Manager manager = new Manager();
    Manager manager2;
    Kundenprojekt kundenprojekt = new Kundenprojekt();

    @BeforeEach
    void setUp(){
        user = User.builder().email("max@mustermann.de").password("testpw12").username("maxMuster").build();
        userService.doCreateUser(user);
        userInDB = userService.findUserByUsernameAndPassword("maxMuster", "testpw12");
        manager = Manager.builder().firstname("hans").name("Hans").user(user).build();
        managerService.doCreateManager(manager);
        manager2 = managerService.findManagerByUserId(userInDB.getId());

    }


    @Test
    void KundenProjektRoundTrip() {
        kundenprojekt = Kundenprojekt.builder().projektname("test").manager(manager2).build();
        kundenprojektService.doCreateKundenprojekt(kundenprojekt);

        //löschen des Projektes und des Managers+Users
        List<Kundenprojekt> kid = kundenprojektService.findAllKundenprojektByManagerId(manager2.getId());
        for(Kundenprojekt k : kid){
            kundenprojektRepository.delete(k);
        }
        managerService.deleteManagerById(manager2.getId());
    }

    @Test
    void findAllPublicKundenprojektTest(){
        List<Kundenprojekt> k = kundenprojektService.findAllPublicKundenprojekt();
        assertEquals(k,k);
        assertEquals(k.hashCode(),k.hashCode());
    }


}