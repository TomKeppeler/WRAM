package org.hbrs.project.wram.model.user.roundTripTests;

import org.hbrs.project.wram.control.reviewer.ReviewerService;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.reviewer.Reviewer;
import org.hbrs.project.wram.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReviewerRoundTripTest {

    User user = new User();
    User userInDB;
    Reviewer reviewer = new Reviewer();
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewerService reviewerService;

    @BeforeEach
    void setUp() {
        user = User.builder().email("max@mustermann.de").password("testpw12").username("maxMuster").build();
        userService.doCreateUser(user);
        userInDB = userService.findUserByUsernameAndPassword("maxMuster", "testpw12");
        reviewer = Reviewer.builder().firstname("hans").name("Hans").user(user).build();
    }


    @Test
    void ManagerRoundTrip() {
        //create
        reviewerService.doCreateReviewer(reviewer);
        //read
        Reviewer reviewer2 = reviewerService.findReviewerByUserId(userInDB.getId());
        //compare
        assertEquals(reviewer, reviewer2);
        assertEquals(reviewer.hashCode(), reviewer.hashCode());
        //delete
        reviewerService.deleteReviewerById(reviewer2.getId());
        //userService.deleteUserById(userInDB.getId()); -> passiert automatisch
    }


}