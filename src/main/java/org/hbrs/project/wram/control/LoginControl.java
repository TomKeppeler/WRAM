package org.hbrs.project.wram.control;


import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LoginControl {

    @Autowired
    private UserRepository repository;

    private User currentUser = null;

    public boolean authenticateUser(String username, String password) throws Exception {
        User user=getUser(username,password);
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
            user = repository.findUserByUsernameAndPassword(username, Encryption.sha256(password));
        } catch (org.springframework.dao.DataAccessResourceFailureException e) {
            // Todo: Create DatabaseException (inside control package?)
            throw new Exception("A failure occurred while trying to connect to a database with JPA.");
        }
        return user;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }


}
