package org.hbrs.project.wram.model.entwickler.profile;

import java.util.UUID;

import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntwicklerProfilDTO {
    private UUID id;
    private int image;
    private String phone;
    private String skills;
    private Entwickler entwickler;
    private Kundenprojekt kundenprojekt;
}
