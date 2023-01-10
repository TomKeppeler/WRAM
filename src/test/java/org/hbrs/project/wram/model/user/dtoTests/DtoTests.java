package org.hbrs.project.wram.model.user.dtoTests;

import org.hbrs.project.wram.model.anfrage.AnfrageDTO;
import org.hbrs.project.wram.model.entwickler.EntwicklerDTO;
import org.hbrs.project.wram.model.kundenprojekt.KundenprojektDTO;
import org.hbrs.project.wram.model.manager.ManagerDTO;
import org.hbrs.project.wram.model.reviewer.ReviewerDTO;
import org.hbrs.project.wram.model.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DtoTests {

    @Test
    void test1() {
        UserDTO u = new UserDTO();
        assertEquals(u.getUsername(), u.getUsername());
        assertEquals(u.getId(), u.getId());
        assertEquals(u.getEmail(), u.getEmail());
        assertEquals(u.getPassword(), u.getPassword());
        assertEquals(u.getVerificationCode(), u.getVerificationCode());
    }

    @Test
    void test2() {
        EntwicklerDTO e = new EntwicklerDTO();
        assertEquals(e.getFirstname(), e.getFirstname());
        assertEquals(e.getName(), e.getName());
        assertEquals(e.getId(), e.getId());
        assertEquals(e.getKundenprojekt(), e.getKundenprojekt());
        assertEquals(e.getPhone(), e.getPhone());
        assertEquals(e.getUser(), e.getUser());
        assertEquals(e.getImage(), e.getImage());
        assertEquals(e.getSkills(), e.getSkills());
    }

    @Test
    void test3() {
        ManagerDTO m = new ManagerDTO();
        assertEquals(m.getFirstname(), m.getFirstname());
        assertEquals(m.getName(), m.getName());
        assertEquals(m.getUser(), m.getUser());
        assertEquals(m.getId(), m.getId());
    }

    @Test
    void test4() {
        AnfrageDTO a = new AnfrageDTO();
        assertEquals(a.getId(), a.getId());
        assertEquals(a.getReason(), a.getReason());
        assertEquals(a.getAccepted(), a.getAccepted());
        assertEquals(a.getReviewer(), a.getReviewer());
        assertEquals(a.getEntwicklerProfil(), a.getEntwicklerProfil());
        assertEquals(a.getKundenprojekt(), a.getKundenprojekt());
    }

    @Test
    void test5() {
        KundenprojektDTO k = new KundenprojektDTO();
        assertEquals(k.getId(), k.getId());
        assertEquals(k.isPublicProjekt(), k.isPublicProjekt());
        assertEquals(k.getManager(), k.getManager());
        assertEquals(k.getProjektname(), k.getProjektname());
        assertEquals(k.getProjektbeschreibung(), k.getProjektbeschreibung());
        assertEquals(k.getSkills(), k.getSkills());
    }

    @Test
    void test6() {
        ReviewerDTO r = new ReviewerDTO();

        assertEquals(r.getFirstname(), r.getFirstname());
        assertEquals(r.getName(), r.getName());
        assertEquals(r.getUser(), r.getUser());
        assertEquals(r.getId(), r.getId());
    }


}