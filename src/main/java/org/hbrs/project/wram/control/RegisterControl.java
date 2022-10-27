package org.hbrs.project.wram.control;

import org.hbrs.project.wram.control.factory.EntityFactory;
import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerDTO;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerRepository;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.manager.ManagerDTO;
import org.hbrs.project.wram.model.manager.ManagerRepository;
import org.hbrs.project.wram.model.reviewer.Reviewer;
import org.hbrs.project.wram.model.reviewer.ReviewerDTO;
import org.hbrs.project.wram.model.reviewer.ReviewerRepository;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterControl {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntwicklerRepository entwicklerRepository;
    @Autowired
    private ReviewerRepository reviewerRepository;
    @Autowired
    private ManagerRepository managerRepository;


    public static boolean passwortCheck(String passwort){
        boolean longEnough = passwort.length() > 7;
        boolean hasNumber = Utils.hasNumber(passwort);
        boolean hasUppercaseLetter = Utils.hasUpperCaseLetter(passwort);
        return longEnough && hasNumber && hasUppercaseLetter;
    }
    public boolean saveUserAndEntwickler(UserDTO userDTO, EntwicklerDTO entwicklerDTO) {
        // Mithilfe der Methode createUser erzeugen wir eine User-Entity, die
        // dann in der Datenbank abgespeichert wird
        User user = EntityFactory.createUser(userDTO);
        userRepository.save(user);
        Entwickler entwickler = EntityFactory.createEntwickler(entwicklerDTO, user);
        entwicklerRepository.save(entwickler);
        return true;
    }

    public boolean saveUserAndManager(UserDTO userDTO, ManagerDTO managerDTO){
        User user = EntityFactory.createUser(userDTO);
        userRepository.save(user);
        Manager manager = EntityFactory.createManager(managerDTO, user);
        managerRepository.save(manager);
        return true;
    }

    public boolean saveUserAndReviewer(UserDTO userDTO, ReviewerDTO reviewerDTO){
        User user = EntityFactory.createUser(userDTO);
        userRepository.save(user);
        Reviewer reviewer = EntityFactory.createReviewer(reviewerDTO, user);
        reviewerRepository.save(reviewer);
        return true;
    }



    // Überprüfen, ob übergebene userDTO-Email schon in der Datenbank vorhanden ist
    public boolean isEmailAlreadyInDatabase(UserDTO userDTO) {
        return userRepository.isEmailInUse(userDTO.getEmail());
    }

    public boolean isUsernameAlreadyInDatabase(UserDTO userDTO) {
        return userRepository.isUsernameInUse(userDTO.getUsername());
    }

}
