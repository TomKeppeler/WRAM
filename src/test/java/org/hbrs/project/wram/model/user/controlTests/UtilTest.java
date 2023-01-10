package org.hbrs.project.wram.model.user.controlTests;

import org.hbrs.project.wram.util.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class UtilTest {


    @Test
    void UtilTest() {
        assertTrue(Utils.hasNumber("Test3"));
        assertFalse(Utils.hasNumber("asd"));
        assertTrue(Utils.hasUpperCaseLetter("Hallo"));
        assertFalse(Utils.hasUpperCaseLetter("dasd"));
        assertTrue(Utils.emailadresseCheck("hallo@gmx.de"));
        assertFalse(Utils.emailadresseCheck("dasd"));
        assertTrue(Utils.isAlpha("Hallo"));
        assertFalse(Utils.isAlpha("543fdsf-DAS"));
    }


}