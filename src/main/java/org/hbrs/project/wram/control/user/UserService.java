/**
 * @outhor Salah  & Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 14.11.22 by Salah
 */
package org.hbrs.project.wram.control.user;

import com.vaadin.flow.component.UI;
import org.hbrs.project.wram.control.entwickler.user.EntwicklerService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.reviewer.ReviewerService;
import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.reviewer.Reviewer;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
/**
 * dint der CRUD von User Daten von DB
 * statt Repository
 */
public class UserService {

    // dint der CRUD von User Daten von DB
    @Autowired
    private UserRepository userRepository;

    //dint um die Rolle der User Daten von DB
    @Autowired
    private ManagerService managerService;

    //dint um die Rolle der User Daten von DB
    @Autowired
    private EntwicklerService entwicklerService;

    //dint um die Rolle der User Daten von DB
    @Autowired
    private ReviewerService reviewerService;


    // User in DB mittel userRepository speichern
    public User doCreateUser(User user) {
        return this.userRepository.save(user);
    }

    // Überprüfen, ob übergebene userDTO-Email schon in der Datenbank vorhanden ist
    public boolean isEmailAlreadyInDatabase(UserDTO userDTO) {
        return userRepository.isEmailInUse(userDTO.getEmail());
    }

    // Überprüfen, ob übergebene userDTO-Username schon in der Datenbank vorhanden ist

    public boolean isUsernameAlreadyInDatabase(UserDTO userDTO) {
        return userRepository.isUsernameInUse(userDTO.getUsername());
    }


    /**
     * überprüfe welche Rolle der User hat
     * @return Rolle m: Manager e: Entwickler, r : Reviewer
     */
    public String getRolle(){
        Manager manager = managerService.getByUserId((UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER));

        if (manager!= null){
            return "m";

        }
        Entwickler entwickler = entwicklerService.getByUserId((UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER));
        if (entwickler!= null){
            return "e";

        }
        Reviewer reviewer = reviewerService.getByUserId((UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER));

        if (reviewer!= null){
            return "r";

        }

        return "User ist nicht zugeortnet";
    }

    /**
     *   find alle User, die im DB vorhandn sind
     * @return Liste aller User
     */
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    /**#
     *       find  User die von DB mit username und pw
     * @param username
     * @param pw
     * @return ein User
     */
    public User findUserByUsernameAndPassword(String username, String pw){
        return this.userRepository.findUserByUsernameAndPassword(username, pw);
    }

    /**
     * User mit ID x wird von DB gelöscht
     * @param id
     */
    public void deleteUserById(UUID id) {
        this.userRepository.deleteById(id);
    }
}