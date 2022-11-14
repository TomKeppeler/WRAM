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

    //um ein User um DB zu speichern
    @Autowired
    private UserRepository userRepository;

    //um ein Entwickler um DB zu speichern
    @Autowired
    private EntwicklerRepository entwicklerRepository;

    //um ein Reviewer um DB zu speichern
    @Autowired
    private ReviewerRepository reviewerRepository;

    //um ein Manager um DB zu speichern
    @Autowired
    private ManagerRepository managerRepository;


    /**
     * Prüfe ob passwort >8 und ein grossen und kein Buchstabe hat
     * @param passwort
     * @return gibt True falls alle Anforderungen erfüllt sind
     */
    public static boolean passwortCheck(String passwort){
        boolean longEnough = passwort.length() > 7;
        boolean hasNumber = Utils.hasNumber(passwort);
        boolean hasUppercaseLetter = Utils.hasUpperCaseLetter(passwort);
        return longEnough && hasNumber && hasUppercaseLetter;
    }

    /**
     * user und Entwickler werden mittels Repository in DB gespeichert
     * @param userDTO wird vorher mittels  createUser() zu Entity umgewandelt
     * @param entwicklerDTO  wird vorher mittels  createEntwickler() zu Entity umgewandelt
     * @return true falls beide Entity erfolgreich gespeichert
     */
    public boolean saveUserAndEntwickler(UserDTO userDTO, EntwicklerDTO entwicklerDTO) {
        // Mithilfe der Methode createUser erzeugen wir eine User-Entity, die
        // dann in der Datenbank abgespeichert wird
        User user = EntityFactory.createUser(userDTO);
        userRepository.save(user);
        Entwickler entwickler = EntityFactory.createEntwickler(entwicklerDTO, user);
        entwicklerRepository.save(entwickler);
        return true;
    }

    /**
     * * user und Manager werden mittels Repository in DB gespeichert
     * @param userDTO wird vorher mittels  createUser() zu Entity umgewandelt
     * @param managerDTO wird vorher mittels  createManager() zu Entity umgewandelt
     * @return true falls beide Entity erfolgreich gespeichert
     */
    public boolean saveUserAndManager(UserDTO userDTO, ManagerDTO managerDTO){
        User user = EntityFactory.createUser(userDTO);
        userRepository.save(user);
        Manager manager = EntityFactory.createManager(managerDTO, user);
        managerRepository.save(manager);
        return true;
    }

    /**
     * * user und Reviewer werden mittels Repository in DB gespeichert
     * @param userDTO wird vorher mittels  createUser() zu Entity umgewandelt
     * @param reviewerDTO wird vorher mittels  createReviewer() zu Entity umgewandelt
     * @return true falls beide Entity erfolgreich gespeichert
     */
    public boolean saveUserAndReviewer(UserDTO userDTO, ReviewerDTO reviewerDTO){
        User user = EntityFactory.createUser(userDTO);
        userRepository.save(user);
        Reviewer reviewer = EntityFactory.createReviewer(reviewerDTO, user);
        reviewerRepository.save(reviewer);
        return true;
    }
}
