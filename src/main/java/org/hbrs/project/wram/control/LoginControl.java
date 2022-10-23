package org.hbrs.project.wram.control;

import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginControl {

    @Autowired
    private UserRepository repository;

    // ToDo: resolve Mapping between User and UserDTO
    private User user = null;

    public boolean authenticateUser(String username, String password) throws Exception {
        User user = getUser(username, password);
        if (user == null) {
            return false;
        } else {
            this.user = user;
        }
        return true;
    }

    private User getUser(String username, String password) throws Exception {
        User user;
        try {
            user = repository.findByUsernameAndPasswort(username, password);
        } catch (org.springframework.dao.DataAccessResourceFailureException e) {
            // Todo: Create DatabaseException (inside control package?)
            throw new Exception("A failure occurred while trying to connect to a database with JPA.");
        }
        return user;
    }
}
