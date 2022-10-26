package org.hbrs.project.wram.control.user;

import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
       UserDTO dto = UserDTO.builder().username(user.getUsername()).id(user.getId()).email(user.getEmail()).passwort(user.getPassword()).build();
       return dto;
    }

}
