package org.hbrs.project.wram.control.user;

import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {

    private UserRepository userRepository;

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

    
  
    public static boolean passwortCheck(String passwort){
        boolean longEnough = passwort.length() > 7;
        boolean hasNumber = Utils.hasNumber(passwort);
        boolean hasUppercaseLetter = Utils.hasUpperCaseLetter(passwort);
        return longEnough && hasNumber && hasUppercaseLetter;
    }
}