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
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ManagerService managerService;

    @Autowired
    private EntwicklerService entwicklerService;

    @Autowired
    private ReviewerService reviewerService;


    public User doCreateUser(User user) {
        return this.userRepository.save(user);
    }

    // Überprüfen, ob übergebene userDTO-Email schon in der Datenbank vorhanden ist
    public boolean isEmailAlreadyInDatabase(UserDTO userDTO) {
        return userRepository.isEmailInUse(userDTO.getEmail());
    }

    

    public boolean isUsernameAlreadyInDatabase(UserDTO userDTO) {
        return userRepository.isUsernameInUse(userDTO.getUsername());
    }


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

    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    public User findUserByUsernameAndPassword(String username, String pw){
        return this.userRepository.findUserByUsernameAndPassword(username, pw);
    }

    public void deleteUserById(UUID id) {
        this.userRepository.deleteById(id);
    }
}