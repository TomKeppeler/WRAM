package org.hbrs.project.wram.model.user.controlTests;

import org.hbrs.project.wram.control.LoginControl;
import org.hbrs.project.wram.control.RegisterControl;
import org.hbrs.project.wram.control.entwickler.user.EntwicklerService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.reviewer.ReviewerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerDTO;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.manager.ManagerDTO;
import org.hbrs.project.wram.model.reviewer.Reviewer;
import org.hbrs.project.wram.model.reviewer.ReviewerDTO;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterControlTest {

    @Autowired
    private RegisterControl registerControl;

    @Autowired
    private EntwicklerService entwicklerService;

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private ManagerService managerService;


    @Autowired
    private UserService userService;

    UserDTO userDTO;
    EntwicklerDTO entwicklerDTO;
    ReviewerDTO reviewerDTO;
    ManagerDTO managerDTO;

    @BeforeEach
    void setUp(){
        userDTO = UserDTO.builder().email("max@mustermann.de").password("testpw12").username("maxMuster").build();
        entwicklerDTO = EntwicklerDTO.builder().firstname("Hans").name("Wurst").build();
        reviewerDTO = ReviewerDTO.builder().firstname("Hans").name("Wurst").build();
        managerDTO = ManagerDTO.builder().firstname("Hans").name("Wurst").build();
    }

    @Test
    void testPasswordCheck() throws Exception {
        assertTrue(RegisterControl.passwortCheck("Test12345"));
    }

    @Test
    void saveUserAndEntwicklerTest(){
        registerControl.saveUserAndEntwickler(userDTO, entwicklerDTO);
        UUID uid = userService.findUserByUsernameAndPassword("maxMuster", "testpw12").getId();
        Entwickler entwickler2 = entwicklerService.findEntwicklerByUserId(uid);
        entwicklerService.deleteEntwicklerById(entwickler2.getId());
    }

    @Test
    void saveUserAndManagerTest(){
        registerControl.saveUserAndManager(userDTO, managerDTO);
        UUID uid = userService.findUserByUsernameAndPassword("maxMuster", "testpw12").getId();
        Manager manager2 = managerService.findManagerByUserId(uid);
        managerService.deleteManagerById(manager2.getId());
    }

    @Test
    void saveUserAndReviewerTest(){
        registerControl.saveUserAndReviewer(userDTO, reviewerDTO);
        UUID uid = userService.findUserByUsernameAndPassword("maxMuster", "testpw12").getId();
        Reviewer reviewer2 = reviewerService.findReviewerByUserId(uid);
        reviewerService.deleteReviewerById(reviewer2.getId());
    }

    @AfterEach
    void deleteFromDB(){

    }

}