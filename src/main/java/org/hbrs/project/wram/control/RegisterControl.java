/**
 * @outhor Salah & Sophia
 * @vision 1.0
 * @Zuletzt_bearbeiret: 14.11.22 by Salah
 */
package org.hbrs.project.wram.control;

import org.hbrs.project.wram.control.factory.EntityFactory;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.entwickler.EntwicklerDTO;
import org.hbrs.project.wram.model.entwickler.EntwicklerRepository;
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

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

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


    @Autowired
    private UserService userService;


    /**
     * Prüfe ob passwort >8 und ein grossen und kein Buchstabe hat
     *
     * @param passwort
     * @return gibt True falls alle Anforderungen erfüllt sind
     */
    public static boolean passwortCheck(String passwort) {
        boolean longEnough = passwort.length() > 7;
        boolean hasNumber = Utils.hasNumber(passwort);
        boolean hasUppercaseLetter = Utils.hasUpperCaseLetter(passwort);
        return longEnough && hasNumber && hasUppercaseLetter;
    }

    /**
     * user und Entwickler werden mittels Repository in DB gespeichert
     *
     * @param userDTO       wird vorher mittels  createUser() zu Entity umgewandelt
     * @param entwicklerDTO wird vorher mittels  createEntwickler() zu Entity umgewandelt
     * @return true falls beide Entity erfolgreich gespeichert
     */
    public boolean saveUserAndEntwickler(UserDTO userDTO, EntwicklerDTO entwicklerDTO) {
        // Mithilfe der Methode createUser erzeugen wir eine User-Entity, die
        // dann in der Datenbank abgespeichert wird

        Entwickler entwickler = EntityFactory.createEntwickler(entwicklerDTO, saveUser(userDTO));
        entwicklerRepository.save(entwickler);
        return true;
    }

    /**
     * * user und Manager werden mittels Repository in DB gespeichert
     *
     * @param userDTO    wird vorher mittels  createUser() zu Entity umgewandelt
     * @param managerDTO wird vorher mittels  createManager() zu Entity umgewandelt
     * @return true falls beide Entity erfolgreich gespeichert
     */
    public boolean saveUserAndManager(UserDTO userDTO, ManagerDTO managerDTO) {

        Manager manager = EntityFactory.createManager(managerDTO, saveUser(userDTO));
        managerRepository.save(manager);
        return true;
    }

    /**
     * * user und Reviewer werden mittels Repository in DB gespeichert
     *
     * @param userDTO     wird vorher mittels  createUser() zu Entity umgewandelt
     * @param reviewerDTO wird vorher mittels  createReviewer() zu Entity umgewandelt
     * @return true falls beide Entity erfolgreich gespeichert
     */
    public boolean saveUserAndReviewer(UserDTO userDTO, ReviewerDTO reviewerDTO) {

        Reviewer reviewer = EntityFactory.createReviewer(reviewerDTO, saveUser(userDTO));
        reviewerRepository.save(reviewer);
        return true;
    }

    /**
     * * user wird mittels Repository in DB gespeichert und registriert
     *
     * @param userDTO wird vorher mittels  createUser() zu Entity umgewandel
     * @return User Instanz
     */
    private User saveUser(UserDTO userDTO) {
        User user = EntityFactory.createUser(userDTO);
        userRepository.save(user);
        try {
            userService.register(user, "sepp-test.inf.h-brs.de:8080/WAC-0.0.1-SNAPSHOT");
        } catch (UnsupportedEncodingException | MessagingException e) {

        }
        return user;
    }


}
