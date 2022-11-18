package org.hbrs.project.wram.control.factory;

import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerDTO;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.manager.ManagerDTO;
import org.hbrs.project.wram.model.reviewer.Reviewer;
import org.hbrs.project.wram.model.reviewer.ReviewerDTO;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;

/*Factory Klasse zur Erzeugung von User, Entwickler, Manager & Reviewer Entities*/

public class EntityFactory {
    public static User createUser(UserDTO userDTO) {
        User user = new User();
        // Die Daten werden einzeln gesetzt
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        return user;
    }

    public static Entwickler createEntwickler(EntwicklerDTO entwicklerDTO, User user) {
        Entwickler entwickler = new Entwickler();
        entwickler.setFirstname(entwicklerDTO.getFirstname());
        entwickler.setName(entwicklerDTO.getName());
        entwickler.setUser(user);
        return entwickler;
    }

    public static Manager createManager(ManagerDTO managerDTO, User user){
        Manager manager = new Manager();
        manager.setFirstname(managerDTO.getFirstname());
        manager.setName(managerDTO.getName());
        manager.setUser(user);
        return manager;
    }

    public static Reviewer createReviewer(ReviewerDTO reviewerDTO, User user){
        Reviewer reviewer = new Reviewer();
        reviewer.setFirstname(reviewerDTO.getFirstname());
        reviewer.setName(reviewerDTO.getName());
        reviewer.setUser(user);
        return reviewer;
    }


}
