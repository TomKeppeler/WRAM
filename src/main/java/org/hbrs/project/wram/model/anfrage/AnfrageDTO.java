/**
 * @outhor Tom
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 *
 */
package org.hbrs.project.wram.model.anfrage;

import java.util.UUID;

import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.reviewer.Reviewer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**Data Transfer Object für Anfragen */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnfrageDTO {
    private UUID id;
    private String reason;
    private String accepted;
    private Reviewer reviewer;
    private Entwickler entwicklerProfil;
    private Kundenprojekt kundenprojekt;
}
