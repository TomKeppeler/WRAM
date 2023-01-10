package org.hbrs.project.wram.model.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    User user = User.builder().email("s").password("1").username("s1").build();

    @Test
    void findUserByUsernameAndPassword() {
        userRepository.save(user);
        User user1 = userRepository.findUserByUsernameAndPassword("s1", "1");
        assertNotNull(user1);

        assertEquals(user1.getPassword(), user.getPassword());
        userRepository.delete(user1);
        assertNull(userRepository.findUserByUsernameAndPassword("s1", "1"));
    }

    @Test
    void isEmailInUse() {
    }


}