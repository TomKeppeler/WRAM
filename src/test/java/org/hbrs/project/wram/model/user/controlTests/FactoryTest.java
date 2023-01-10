package org.hbrs.project.wram.model.user.controlTests;

import org.hbrs.project.wram.model.entwickler.EntwicklerDTO;
import org.hbrs.project.wram.model.manager.ManagerDTO;
import org.hbrs.project.wram.model.reviewer.ReviewerDTO;
import org.hbrs.project.wram.model.user.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class FactoryTest {

    UserDTO userDTO;
    EntwicklerDTO entwicklerDTO;
    ReviewerDTO reviewerDTO;
    ManagerDTO managerDTO;

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder().email("max@mustermann.de").password("testpw12").username("maxMuster").build();
        entwicklerDTO = EntwicklerDTO.builder().firstname("Hans").name("Wurst").build();
        reviewerDTO = ReviewerDTO.builder().firstname("Hans").name("Wurst").build();
        managerDTO = ManagerDTO.builder().firstname("Hans").name("Wurst").build();
    }

    @Test
    void test() {
        /*
          User user = EntityFactory.createUser(userDTO);
          Entwickler entwickler = EntityFactory.createEntwickler(entwicklerDTO, user);
          Reviewer reviewer = EntityFactory.createReviewer(reviewerDTO, user);
          Manager manager = EntityFactory.createManager(managerDTO, user);
         */

    }

    @AfterEach
    void deleteFromDB() {

    }

}