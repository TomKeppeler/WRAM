package org.hbrs.project.wram.control.entwickler;

import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.entwickler.user.EntwicklerDTO;
import org.springframework.stereotype.Service;

@Service
public class EntwicklerService {
    public EntwicklerDTO toDTO(Entwickler entwickler) {
        EntwicklerDTO dto = EntwicklerDTO.builder().id(entwickler.getId()).name(entwickler.getName())
                .firstname(entwickler.getFirstname()).user(entwickler.getUser()).build();
        return dto;
    }
}
