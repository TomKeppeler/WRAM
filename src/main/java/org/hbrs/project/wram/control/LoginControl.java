package org.hbrs.project.wram.control;

import com.vaadin.flow.component.UI;
import org.hbrs.project.wram.control.user.UserService;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Constants;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginControl {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService userService;
    private UserDTO currentUser = null;

    public boolean authenticateUser(String username, String password) throws Exception {
        UserDTO user = userService.toDTO(getUser(username, password));
        if (user == null) {
            return false;
        } else {
            this.currentUser = user;
        }
        return true;
    }

    private @Nullable User getUser(String username, String password) throws Exception {
        User user;
        try {
            user = repository.findUserByUsernameAndPassword(username, password);
        } catch (org.springframework.dao.DataAccessResourceFailureException e) {
            // Todo: Create DatabaseException (inside control package?)
            throw new Exception("A failure occurred while trying to connect to a database with JPA.");
        }
        return user;
    }

    public UserDTO getCurrentUser() {
        return this.currentUser;
    }

    public void logout() {
        UI.getCurrent().getSession().close();
        this.currentUser = null;
        UI.getCurrent().navigate(Constants.Pages.MAIN_VIEW);
    }


}
