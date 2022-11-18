package org.hbrs.project.wram.model.anfrage;

import java.util.UUID;

import org.hbrs.project.wram.model.entwickler.profile.EntwicklerProfil;
import org.hbrs.project.wram.model.kundenprojekt.Kundenprojekt;
import org.hbrs.project.wram.model.reviewer.Reviewer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Data Transfer Object für Anfragen */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnfrageDTO {
    private UUID id;
    private String reason;
    private String accepted;
    private Reviewer reviewer;
    private EntwicklerProfil entwicklerProfil;
    private Kundenprojekt kundenprojekt;
}
