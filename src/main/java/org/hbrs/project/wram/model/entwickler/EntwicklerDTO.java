/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.model.entwickler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.user.User;

import java.util.UUID;

/** Data Transfer Object für Entwickler, um ein entwickler zu ertellen */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntwicklerDTO {
    private UUID id;
    private String name;
    private String firstname;
    private User user;
    private byte[] image;
    private String phone;
    private String skills;
    private Kundenprojekt kundenprojekt;
}
