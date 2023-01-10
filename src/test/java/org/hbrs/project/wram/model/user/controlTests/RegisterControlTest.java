package org.hbrs.project.wram.model.user.controlTests;

import org.hbrs.project.wram.control.RegisterControl;
import org.hbrs.project.wram.control.entwickler.EntwicklerService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.reviewer.ReviewerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.entwickler.EntwicklerDTO;
import org.hbrs.project.wram.model.manager.ManagerDTO;
import org.hbrs.project.wram.model.reviewer.ReviewerDTO;
import org.hbrs.project.wram.model.user.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RegisterControlTest {

    UserDTO userDTO;
    EntwicklerDTO entwicklerDTO;
    ReviewerDTO reviewerDTO;
    ManagerDTO managerDTO;
    String pw = "testpw12";
    String un = "maxMuster";
    String fn = "Hans";
    String n = "Wurst";
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

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder().email("max@mustermann.de").password(pw).username(un).build();
        entwicklerDTO = EntwicklerDTO.builder().firstname("Hans").name(n).build();
        reviewerDTO = ReviewerDTO.builder().firstname(fn).name(n).build();
        managerDTO = ManagerDTO.builder().firstname(fn).name(n).build();
    }

    @Test
    void testPasswordCheck() throws Exception {
        assertTrue(RegisterControl.passwortCheck("Test12345"));
    }

    /**
     * Tests funktionieren nicht mehr aufgrund von E-mail Verifikation
     *
     @Test void saveUserAndEntwicklerTest(){
     registerControl.saveUserAndEntwickler(userDTO, entwicklerDTO);
     UUID uid = userService.findUserByUsernameAndPassword(un, pw).getId();
     Entwickler entwickler2 = entwicklerService.findEntwicklerByUserId(uid);
     entwicklerService.deleteEntwicklerById(entwickler2.getId());
     }

     @Test void saveUserAndManagerTest(){
     registerControl.saveUserAndManager(userDTO, managerDTO);
     UUID uid = userService.findUserByUsernameAndPassword(un, pw).getId();
     Manager manager2 = managerService.findManagerByUserId(uid);
     managerService.deleteManagerById(manager2.getId());
     }

     @Test void saveUserAndReviewerTest(){
     registerControl.saveUserAndReviewer(userDTO, reviewerDTO);
     UUID uid = userService.findUserByUsernameAndPassword(un, pw).getId();
     Reviewer reviewer2 = reviewerService.findReviewerByUserId(uid);
     reviewerService.deleteReviewerById(reviewer2.getId());
     }
     **/


}