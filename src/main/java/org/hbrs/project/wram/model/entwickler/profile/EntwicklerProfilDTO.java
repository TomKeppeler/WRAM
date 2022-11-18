/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 *
 */
package org.hbrs.project.wram.model.entwickler.profile;

import java.util.UUID;

import org.hbrs.project.wram.model.entwickler.user.Entwickler;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Data Transfer Object für Entwicklerprofile */

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
