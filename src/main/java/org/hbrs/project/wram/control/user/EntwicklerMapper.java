package org.hbrs.project.wram.control.user;

import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerDTO;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.springframework.stereotype.Component;


@Component
public class EntwicklerMapper {

    public EntwicklerDTO toDTO(Entwickler entwickler) {
       EntwicklerDTO dto = EntwicklerDTO.builder().id(entwickler.getId()).name(entwickler.getName()).firstname(entwickler.getFirstname()).user(entwickler.getUser()).build();
       return dto;
    }

}
