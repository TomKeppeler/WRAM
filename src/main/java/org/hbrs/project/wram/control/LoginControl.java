/**
 * @outhor Salah & Sophia
 * @vision 1.0
 * @Zuletzt bearbeiret: 14.11.22 by Salah
 */

package org.hbrs.project.wram.control;

import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class LoginControl {

    // um User von der DB zu hollen
    @Autowired
    private UserRepository repository;

    // wird von DB mitls authenticateUser() geholt
    private User currentUser = null;

    /**
     *  prüft ob user vorhanden mit getUsergetUser()
     * @param username für Login
     * @param password
     * @return true falls der user vorhanden ist false falls nicht
     * @throws Exception wird von getUser() delegiert
     */
    public boolean authenticateUser(String username, String password) throws Exception {
        User user = getUser(username, password);
        // user.isVerified();
        if (user == null || !user.isVerified()) {
            throw new IllegalAccessException();


        } else {
            this.currentUser = user;
        }
        return true;
    }


    /**
     * wird in  authenticateUser() benutzt um user von DB zu hollen
     * @param username
     * @param password
     * @return gibt ein user zurück
     * @throws Exception falls spring nicht funktioniert
     */
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

    public @Nullable boolean isUsernameInUse(String username) {


        return repository.isUsernameInUse(username);


    }

    /**
     *
     * @return gibt ein user zurück was bei authenticateUser() geholt wurde
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * sets currentUser object variable of this class.
     * IMPORTANT: because when rerouting to LoginView, the LoginControl instance is not yet destructed.
     * And its currentUser object is still set to the currentUser
     */
    public void logoutUser() {
        this.currentUser = null;
    }


}
