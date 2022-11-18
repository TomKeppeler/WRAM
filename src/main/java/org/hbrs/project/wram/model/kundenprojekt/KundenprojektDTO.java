package org.hbrs.project.wram.model.kundenprojekt;

import java.util.UUID;

import org.hbrs.project.wram.model.manager.Manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
