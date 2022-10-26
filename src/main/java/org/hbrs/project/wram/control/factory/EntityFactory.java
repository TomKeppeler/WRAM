package org.hbrs.project.wram.control.factory;

import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerDTO;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;

public class EntityFactory {
    public static User createUser(UserDTO userDTO) {
        User user = new User();
        // Die Daten werden einzeln gesetzt
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());

        return user;
    }

    public static Entwickler createEntwickler( EntwicklerDTO entwicklerDTO, User user) {
        Entwickler entwickler = new Entwickler();

        // Die Daten werden einzeln gesetzt
        entwickler.setFirstname(entwicklerDTO.getFirstname());
        entwickler.setName(entwicklerDTO.getName());
        entwickler.setUser(user);

        return entwickler;
    }


}
