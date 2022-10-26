package org.hbrs.project.wram.control;

import com.vaadin.flow.data.validator.EmailValidator;
import org.hbrs.project.wram.control.factory.EntityFactory;
import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerDTO;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerRepository;
import org.hbrs.project.wram.model.manager.ManagerRepository;
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
        entwicklerRepository.save(entwicklerRepository.save(EntityFactory.createEntwickler(entwicklerDTO, user)));
        return true;
    }

    // Überprüfen, ob übergebene userDTO-Email schon in der Datenbank vorhanden ist
    public boolean isEmailAlreadyInDatabase(UserDTO userDTO) {
        return userRepository.isEmailInUse(userDTO.getEmail());
    }

    public boolean isUsernameAlreadyInDatabase(UserDTO userDTO) {
        return false;//userRepository.isUsernameInUse(userDTO.getEmail());
    }
    public static void registerMethod(String vorname,String nachname,String email,String passwort,String rolle){
      /*User u=new User();
      u.setVorname(vorname);
      u.setNachname(nachname);
      u.setEmail(email);
      u.setPasswort(passwort);
      u.setRolle(rolle); */
    }

}
