/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.model.kundenprojekt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hbrs.project.wram.model.manager.Manager;

import java.util.UUID;

/** Data Transfer Object für Kundenprojekte*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KundenprojektDTO {
    private UUID id;
    private boolean publicProjekt;
    private Manager manager;
    private String projektname;
    private String projektbeschreibung;
    private String skills;
}
